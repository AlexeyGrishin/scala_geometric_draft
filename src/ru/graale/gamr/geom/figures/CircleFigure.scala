package ru.graale.gamr.geom.figures

import ru.graale.gamr.geom._
import ru.graale.gamr.geom.WithEpsilon._;

class CircleFigure(val center: Point, radius: Double) extends Figure {

  val leftUp = Point(center.x - radius, center.y - radius)
  val rightDown = Point(center.x + radius, center.y + radius)


  def seeFrom(p: Point) = null

  def position(f: Figure) = Geom.intersect(this, f);

  def position(l: Line): LinePosition.Value = {
    val dist = Geom.distance(center, l);
    val pos = l.position(center)
    if (dist <~ radius) {
      LinePosition.Cross
    }
    else if (dist ~ radius) {
      pos match {case PointPosition.Inside => LinePosition.TangentInside; case _ => LinePosition.TangentOutside}
    }
    else {
      pos match {case PointPosition.Inside => LinePosition.Inside; case _ => LinePosition.Outside}
    }
  }

  def contains(p: Point) = Geom.distance(p, center) <~ radius

}