package ru.graale.gamr.geom.figures

import scala.math._
import ru.graale.gamr.geom.{Geom, Point}
import ru.graale.gamr.geom.WithEpsilon._;
;

class PolygonialCircle(center: Point, radius: Double, quality: Double = 1) extends PolygonialFigure(
  PolygonialCircle.points(center, radius, quality), center
) {
  override def contains(p: Point) = Geom.distance(center, p) <~ radius
}

object PolygonialCircle {
  def points(center: Point, radius: Double, quality: Double = 1) = {
    val minAngle = asin(quality / radius)
    var angles: List[Double] = ((0.0 to (2.0*Pi)) by minAngle).toList
    if (!angles.contains(2*Pi)) {
      angles = 2*Pi :: angles
    }
    angles.map(a => Geom.point(center, a, radius))
  }
}
