package ru.graale.gamr.geom

import ru.graale.gamr.geom.WithEpsilon._;

class Circle(val center: Point, val radius: Double) {

  def contains(p: Point) = Geom.distance(center, p) <~ radius

  def intersect(l: Line): List[Point] = {
    val distance = Geom.distance(center, l)
    if (distance > radius)
      List()
    else {
      val lr = ((l.A ^ 2) + (l.B ^ 2))
      val x0 = (-l.A*l.C) / lr
      val y0 = (-l.B*l.C) / lr
      if (distance ~ radius) {
        List(Point(x0, y0))
      }
      else {
        val d = (radius^2) - (l.C^2) / lr
        val mult = scala.math.sqrt(d / lr)
        val ax = x0 + l.B * mult
        val bx = x0 - l.B * mult
        val ay = y0 - l.A * mult
        val by = y0 + l.A * mult
        List(Point(ax, ay), Point(bx, by))
      }
    }
  }


}