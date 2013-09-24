package ru.graale.gamr.geom

import scala.math._
import java.lang.IllegalArgumentException
import figures._;
import ru.graale.gamr.geom.WithEpsilon._

object Geom {

  def sqr(x: Double) = x*x

  def distance(point1: Point, point2: Point) = sqrt(sqr(point2.x - point1.x) + sqr(point2.y - point1.y))

  def distance(point: Point, line: Line) = abs(line.calculate(point)) / sqrt(sqr(line.A) + sqr(line.B))

  def middle(point1: Point, point2: Point) = Point((point2.x + point1.x)/2, (point2.y + point1.y)/2)

  def intersect(line1: Line, line2: Line): Option[Point] = {
    val dividend = line1.A*line2.B - line1.B*line2.A
    if (dividend == 0) return None;
    val x = (line2.C*line1.B - line1.C*line2.B) / dividend
    val y = (line2.A*line1.C - line1.A*line2.C) / dividend
    Option(Point(x, y))
  }

  def intersect(figure1: Figure, figure2: Figure): FigurePosition.Value = {
    (figure1, figure2) match {
      case (pf1: PolygonialFigure, pf2: PolygonialFigure) =>  pf1.positionPol(pf2)
      case _ => throw new IllegalArgumentException("Cannot find intersection between figure " + figure1 + " and " + figure2)
    }
  }

  def point(point1: Point, angle: Double, distance: Double = 1) = {
    Point(point1.x + distance*cos(angle), point1.y + distance*sin(angle))
  }

  def triangle(point1: Point, point2: Point, point3: Point) = {
    new PolygonialFigure(List(point1, point2, point3))
  }

  def circle(center: Point, radius: Double, quality: Quality = Normal) = {
    val qual: Double = quality match {
      case Best => throw new IllegalArgumentException("Not supported yet");
      case Worst => 5
      case Normal => 1
      case Quality(x) => x
    }
    new PolygonialCircle(center, radius, qual)
  }

  def rectangle(point1: Point, point2: Point) = {
    val rect = bounds(point1, point2);
    new PolygonialFigure(List(Point(rect.left, rect.top), Point(rect.right, rect.top), Point(rect.right, rect.bottom), Point(rect.left, rect.bottom)))
  }

  def bounds(point1: Point, point2: Point): Rectangle = new Rectangle(point1, point2)

  def normalize(angle: Double): Double = {
    if (angle >=0 && angle < 2*Pi)
      angle;
    else if (angle < 0)
      normalize(angle + 2*Pi)
    else
      normalize(angle - 2*Pi)
  }

  def cosBetween(line1: Line, line2: Line) = {
    val a = abs(line1.A*line2.A + line1.B*line2.B)
    val b = sqrt((line1.A^2) + (line1.B^2))
    val c = sqrt((line2.A^2) + (line2.B^2))
    a / (b*c)
  }

}