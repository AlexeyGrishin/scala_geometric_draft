package ru.graale.gamr.geom

import scala.math._
import ru.graale.gamr.geom.WithEpsilon._
import ru.graale.gamr.geom.figures._
;

class Observer(private var position: Point, private var angle: Double = 0.0, private var viewAngle: Double = 60.0) {

  private var line1: Line = null;
  private var line2: Line = null;
  private var dirLine: Line = null;

  updateViewLines();

  def pos = position

  def getAngle = angle;

  def rotate(angle: Double) {
    this.angle += angle;
    if (this.angle > Pi) this.angle -= 2*Pi;
    if (this.angle < -Pi) this.angle += 2*Pi;
    updateViewLines();
  }

  def move(distance: Double, checker: (Point) => Boolean) {
    val newPoint = Geom.point(position, angle, distance)
    if (checker(newPoint)) {
      position = newPoint
      updateViewLines();
    }
  }

  def teleport(p: Point) {
    position = p;
    updateViewLines();
  }

  private def updateViewLines() {
    line1 = new Line(position, leftViewAngle)
    line2 = new Line(position, rightViewAngle)
    dirLine = new Line(position, angle + Pi/2)
    val pointBehindObserver = Geom.point(position, angle)
    line1.orient(pointBehindObserver, PointPosition.Inside)
    line2.orient(pointBehindObserver, PointPosition.Inside)
    dirLine.orient(pointBehindObserver, PointPosition.Inside)
  }

  def viewAngles = List(leftViewAngle, rightViewAngle)


  def rightViewAngle: Double = {
    angle + viewAngle / 2
  }

  def leftViewAngle: Double = {
    angle - viewAngle / 2
  }

  def viewLines = List(line1, line2)

  class LineStartedFrom(val line: Line, val point: Point, val distance: Double) {
    def far(p: Point) = Geom.distance(point, p) >= distance
    def near(p: Point) = Geom.distance(point, p) <= distance
  }

  private def lineDistance(view: (Array[Line], Segment)) = {
    if (view._1.isEmpty) {
      (List(), view._2)
    }
    else {
      val points: List[Point] = if (view._1(0).contains(view._2.startPoint))
        List(view._2.startPoint, view._2.endPoint)
      else
        List(view._2.endPoint, view._2.startPoint)

      val lines = points.zipWithIndex.map((x: (Point, Int)) => {
        new LineStartedFrom(view._1(x._2), position, Geom.distance(position, x._1))
      })
      (lines, view._2)
    }
  }

  def view(figures: List[Figure]): List[Segment] = {
    //1. find lines - done
    //2. find figures which inside the angles
    val figuresInside = figures
      .filter(p => p.position(line1) != LinePosition.Outside)
      .filter(p => p.position(line2) != LinePosition.Outside)
      .filter(p => p.position(dirLine) != LinePosition.Outside)
    //3. get only visible segments
    val tangentsAndSegments = figuresInside.map(f => lineDistance(f.seeFrom(position)))
    val segments = tangentsAndSegments.map(_._2)
    //4. get tangent lines
    val tangentLines = new LineStartedFrom(line1, position, 0) ::
      new LineStartedFrom(line2, position, 0) ::
      tangentsAndSegments.flatMap(_._1.elements.toList)
    val miniSegments: List[Segment] = segments.flatMap(s => {
      val points = tangentLines.flatMap(x => s.intersect(x.line).elements.toList.filter(x.far(_)))
      s.splitBy(points)
    })
    //TODO: OPTIMIZE!!!!
    val visible100 = miniSegments.filter(s => s.positionFromLine(line1) != PointPosition.Outside && s.positionFromLine(line2) != PointPosition.Outside)

    visible100.filter(s => {
      val line = new Line(position, s.center)
      val intersectionPoints = s.intersect(line).elements.toList
      val distance =
        if (!intersectionPoints.isEmpty) intersectionPoints.map(Geom.distance(position, _)).min else Geom.distance(position, s.center)
      val distances = visible100.flatMap(_.intersect(line).elements.toList).map(Geom.distance(position, _));
      distances.isEmpty || distances.min ~ distance
    })
    //miniSegments
  }

}