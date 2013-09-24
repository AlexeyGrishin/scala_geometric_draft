package ru.graale.gamr.geom

class Arc(val center: Point, val radius: Double, _startAngle: Double, _endAngle: Double, val dir: Direction = CounterClockwise) {

  lazy val startAngle = Geom.normalize(_startAngle)
  lazy val endAngle = Geom.normalize(_endAngle)
  lazy val startPoint = Geom.point(center, startAngle, radius)
  lazy val endPoint = Geom.point(center, endAngle, radius)

  lazy val (directedStartAngle, directedEndAngle) = dir.swapIfClockwise(startAngle, endAngle);
  lazy val (directedStartPoint, directedEndPoint) = dir.swapIfClockwise(startPoint, endPoint);

  lazy val length = Geom.normalize(endAngle - startAngle) * radius;

  def invert = new Arc(center, radius, startAngle, endAngle, -dir)
}






