package ru.graale.gamr.geom.figures

import collection.mutable.MutableList
import ru.graale.gamr.geom._

class PolygonialFigure(val points: List[Point], var center: Point = null) extends Figure {

  val leftUp = calculateLeftBound();
  val rightDown = calculateRightBound();
  if (center == null) center = calculateCenter()

  val edges: List[StraightSegment] = calculateEdges();

  private def calculateCenter() = Point((rightDown.x + leftUp.x)/2, (rightDown.y + leftUp.y) / 2)

  private def calculateLeftBound() = Point(points.map(_.x).min, points.map(_.y).min)
  private def calculateRightBound() = Point(points.map(_.x).max, points.map(_.y).max)

  private def calculateEdges() = {
    var edges = new MutableList[StraightSegment]
    for (i <- 0 to points.length - 2) {
      val st = new StraightSegment(points(i), points(i+1))
      if (i == 0) {
        st.orient(points(2), PointPosition.Inside)
      }
      else {
        st.orient(points.head, PointPosition.Inside)
      }
      edges += st;
    }
    val st = new StraightSegment(points.last, points.head)
    st.orient(points(1), PointPosition.Inside)
    edges += st;
    edges.toList
  }

  def seeFrom(p: Point) = {
    val linesFromSource: List[(Point, Line)] = points.map(x => (x, new Line(p, x)))
    val tangents: List[(Point, Line)] = linesFromSource.filter( x => LinePosition.isTangent(position(x._2)))
    if (!tangents.isEmpty) {
      val tline = new Line(tangents.head._1, tangents.last._1)
      tline.orient(p, PointPosition.Inside)
      var visibleSegments = edges.filter( x => x.positionFromLine(tline) == PointPosition.Inside)
      if (visibleSegments.isEmpty)
        visibleSegments = edges.filter( x => x.positionFromLine(tline) == PointPosition.OnLine)

      (tangents.map(_._2).toArray, new CompositeSegment(visibleSegments))
    }
    else {
      (Array(), new CompositeSegment(edges))
    }
  }

  def positionPol(f: PolygonialFigure): FigurePosition.Value = {
    val pointsInside = f.points.filter(contains(_)).length
    val oppositeFigurePointsInside = points.filter(f.contains(_)).length
    val MyPoints = points.length;
    val TheirPoints = f.points.length;
    (pointsInside, oppositeFigurePointsInside) match {
      case (MyPoints, 0) => FigurePosition.Inside;
      case (0, TheirPoints) => FigurePosition.Wrapped;
      case (0, 0) => FigurePosition.Outside;
      case _ => FigurePosition.Intersect
    }
  }

  def position(f: Figure) = Geom.intersect(this, f)

  def position(l: Line): LinePosition.Value = {
    val pointsPoses = points.map(l.position(_))
    val pointsInside = pointsPoses.filter(_ == PointPosition.Inside).length
    val pointsOutside = pointsPoses.filter(_ == PointPosition.Outside).length
    val pointsMatch = pointsPoses.filter(_ == PointPosition.OnLine).length

    if (pointsInside == 0 || pointsOutside == 0) {
      val pos = if (pointsInside == 0) PointPosition.Outside; else PointPosition.Inside
      (pos, pointsMatch) match {
        case (PointPosition.Inside, 0) => LinePosition.Inside
        case (PointPosition.Outside, 0) => LinePosition.Outside
        case (PointPosition.Inside, _) => LinePosition.TangentInside
        case (PointPosition.Outside, _) => LinePosition.TangentOutside
      }
    }
    else {
      LinePosition.Cross;
    }
  }

  def contains(p: Point) = edges.map(_.position(p)).filter(_ == PointPosition.Outside).isEmpty
}