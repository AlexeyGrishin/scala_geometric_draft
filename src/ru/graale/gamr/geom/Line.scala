package ru.graale.gamr.geom

import scala.math._;
import ru.graale.gamr.geom.WithEpsilon._

object PointPosition extends Enumeration {
  val Inside = Value(-1)
  val Outside = Value(+1)
  val OnLine = Value(0)
}

case class Line(A: Double, B: Double, C: Double) {

  def this(p1: Point, p2: Point) = this(p1.y - p2.y, p2.x - p1.x, p2.y * p1.x - p2.x*p1.y)

  def this(p: Point, angle: Double) = this(-sin(angle), cos(angle), p.x*sin(angle) - p.y*cos(angle))

  private var sign: Double = 1

  def position(point: Point) = {
    val i = signum(sign * calculate(point)).toInt
    PointPosition(i);
  }

  def orient(point: Point, pos: PointPosition.Value) {
    if (pos == PointPosition.OnLine) throw new IllegalArgumentException()

    if (position(point) != pos) {sign = -sign}
  }

  def calculate(point: Point) = A*point.x + B*point.y + C

  def intersect(line: Line) = Geom.intersect(this, line);

  def contains(point: Point) = position(point) == PointPosition.OnLine

  def signum(double: Double) = scala.math.signum(double.zerify)


};




