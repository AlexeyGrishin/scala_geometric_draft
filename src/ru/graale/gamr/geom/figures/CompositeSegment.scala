package ru.graale.gamr.geom.figures

import collection.mutable.{HashSet, MutableList}
import ru.graale.gamr.geom._

class CompositeSegment(_segments: List[Segment]) extends Segment {

  if (_segments.isEmpty) throw new IllegalArgumentException("At least one segment shall be specified")

  val segments = {
    var s = new MutableList[Segment];
    var iterate = true;
    def seg(segment: Segment): Boolean = {
      if (s.isEmpty) {
        s += segment
      }
      else if (s.last.endPoint == segment.startPoint) {
        s += segment
      }
      else if (s.last.endPoint == segment.endPoint) {
        s += segment.invert
      }
      else if (s.head.startPoint == segment.endPoint) {
        val newhead = new MutableList[Segment]
        newhead += segment
        s = newhead ++ s
      }
      else if (s.head.startPoint == segment.startPoint) {
        val newhead = new MutableList[Segment]
        newhead += segment.invert
        s = newhead ++ s
      }
      else {
        return false
      }
      true
    }
    var segmentsToProcess = new HashSet[Segment] ++ _segments
    while (iterate && !segmentsToProcess.isEmpty) {
      iterate = false;
      for (segment <- segmentsToProcess) {
        if (seg(segment)) {
          segmentsToProcess -= segment
          iterate = true
        }
      }
    }
    if (s.length != _segments.length) {
      throw new IllegalArgumentException("Cannot link all specified segments: " + _segments)
    }
    s.toList
  }

  lazy val endPoint = segments.last.endPoint
  lazy val startPoint = segments.head.startPoint
  lazy val length = segments.map(_.length).reduce(_ + _)
  lazy val center = Geom.middle(startPoint, endPoint)

  def invert: Segment = new CompositeSegment(segments.reverse.map(_.invert))

  def splitBy(points: List[Point]): List[Segment] = {
    val res = new MutableList[Segment];
    val currentSet = new MutableList[Segment];

    for (segment <- segments) {
      val pointsToSplit = points.filter(x => segment.contains(x) && !segment.isCorner(x))
      if (!pointsToSplit.isEmpty) {
        val splitSegments: List[Segment] = segment.splitBy(pointsToSplit)
        currentSet += splitSegments.head
        res += (if (currentSet.length > 1) new CompositeSegment(currentSet.toList) else currentSet.head)
        currentSet.clear()
        splitSegments.drop(1).reverse.drop(1).reverse.foreach(res += _)
        currentSet += splitSegments.last
      }
      else {
        currentSet += segment
      }
      if (points.elements.toList.contains(segment.endPoint) && segment.endPoint != endPoint) {
        res += (if (currentSet.length > 1) new CompositeSegment(currentSet.toList) else currentSet.head)
        currentSet.clear()
      }
    }
    if (!currentSet.isEmpty)
      res += (if (currentSet.length > 1) new CompositeSegment(currentSet.toList) else currentSet.head)

    res.toList
  }

  def intersect(line: Line) = segments.flatMap(_.intersect(line)).toList

  def contains(point: Point) = !segments.filter(_.contains(point)).isEmpty


}