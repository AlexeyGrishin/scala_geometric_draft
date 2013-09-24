package ru.graale.gamr.geom

import scala.math._;
import ru.graale.gamr.geom.WithEpsilon._

case class Rectangle(left: Double, top: Double, right: Double, bottom: Double) {
  def this(p1: Point, p2: Point) = this(min(p1.x, p2.x), min(p1.y, p2.y), max(p1.x, p2.x), max(p1.y, p2.y))

  val width = right - left
  val height = bottom - top

  def contains(p: Point) = {
    (left <~ p.x) && (p.x <~ right) && (top<~ p.y) && (p.y <~ bottom)
  }
};




