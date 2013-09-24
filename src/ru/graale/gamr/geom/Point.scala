package ru.graale.gamr.geom

import WithEpsilon._

case class Point(x: Double, y: Double) {
  def ~ (p: Point) = Geom.distance(p, this) ~ 0.0
}