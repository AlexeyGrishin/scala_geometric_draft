package ru.graale.gamr.geom.figures

import ru.graale.gamr.geom._
import PointPosition._;

trait Segment {

  def startPoint: Point
  def endPoint: Point
  def length: Double;

  def contains(point: Point): Boolean;
  def intersect(line: Line): List[Point];
  def splitBy(points: List[Point]): List[Segment];
  def center: Point;
  def invert: Segment;

  def isCorner(point: Point) = startPoint == point || endPoint == point

  def positionFromLine(line: Line): PointPosition.Value = {
    val spos = line.position(startPoint);
    val epos = line.position(endPoint)
    (spos, epos) match {
      case (Outside, Outside)
           | (Outside, OnLine)
           | (OnLine, Outside) => Outside
      case (Inside, Inside)
           | (Inside, OnLine)
           | (OnLine, Inside) => Inside
      case _ => OnLine
    }
  }

}