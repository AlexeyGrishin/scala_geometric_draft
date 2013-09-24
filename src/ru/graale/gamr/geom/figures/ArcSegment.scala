package ru.graale.gamr.geom.figures
import ru.graale.gamr.geom._

class ArcSegment(arc: Arc) extends Segment {
  lazy val startPoint = arc.directedStartPoint
  lazy val endPoint = arc.directedEndPoint
  val center = arc.center
  lazy val length = arc.length

  def invert = new ArcSegment(arc.invert)

  def splitBy(points: List[Point]) = null

  def intersect(line: Line) = null

  def contains(point: Point) = false


}