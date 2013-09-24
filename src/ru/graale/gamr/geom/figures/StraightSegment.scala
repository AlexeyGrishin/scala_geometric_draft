package ru.graale.gamr.geom.figures

import scala.collection.mutable.MutableList
import ru.graale.gamr.geom._
import ru.graale.gamr.geom.PointPosition._
import scala.math._
import scala._
;

class StraightSegment(val startPoint: Point, val endPoint: Point) extends Segment {

  private val declLine = new Line(startPoint, endPoint);
  lazy val center = Geom.middle(startPoint, endPoint)
  lazy val length = Geom.distance(startPoint, endPoint)
  lazy val bounds = Geom.bounds(startPoint, endPoint);
  lazy private val direction = (signum(endPoint.x - startPoint.x).toInt, signum(endPoint.y - startPoint.y).toInt)

  def splitBy(points: List[Point]): List[Segment] = {
    checkContains(points)
    val segments = new MutableList[Segment]();
    val sortedPoints = points
      .sortBy(p => p.x * direction._1)
      .sortBy(p => p.y * direction._2)

    val startPoints: List[Point] = (List[Point](startPoint) ::: sortedPoints)
    val endPoints: List[Point] = sortedPoints ::: List[Point](endPoint)
    for (i <- 0 to startPoints.length - 1) {
      segments += new StraightSegment(startPoints(i), endPoints(i))
    }
    segments.toList
  }

  def invert: Segment = new StraightSegment(endPoint, startPoint)

  def visibilityFactor(pointOfView: Point): Double = 1-abs(Geom.cosBetween(declLine, new Line(pointOfView, center)))

  private def checkContains(points: List[Point]) {
    points.foreach { x =>
      if (!contains(x)) throw new IllegalArgumentException("Point " + x + " is not on the line")
    }
  }

  def intersect(line: Line): List[Point] = {
    val spos = line.position(startPoint);
    val epos = line.position(endPoint)
    if (spos != epos && spos != OnLine && epos != OnLine) {
      return Geom.intersect(line, declLine).toList
    }
    List()
  }


  def orient(point: Point, pos: PointPosition.Value) { declLine.orient(point, pos) }

  def position(point: Point) = declLine.position(point);


  def contains(point: Point) = {
    bounds.contains(point) && declLine.position(point) == OnLine
  }



  override def toString = startPoint + " - " + endPoint
}