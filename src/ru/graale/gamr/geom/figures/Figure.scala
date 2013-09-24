package ru.graale.gamr.geom.figures

import java.lang.Boolean
import ru.graale.gamr.geom._

object LinePosition extends Enumeration {
  val Outside = Value(PointPosition.Outside.id)
  val Inside = Value(PointPosition.Inside.id)
  val Cross = Value(0)
  val TangentOutside = Value(PointPosition.Outside.id * 2)
  val TangentInside = Value(PointPosition.Inside.id * 2)

  def isTangent(v: Value) = (v == TangentInside || v == TangentOutside);
}

object FigurePosition extends Enumeration {
  val Inside = Value
  val Intersect = Value
  val Outside = Value
  val Wrapped = Value
}

abstract class Figure extends GeometricObject {

  def center: Point;

  def leftUp: Point
  def rightDown: Point
  def bounds = new Rectangle(leftUp, rightDown)

  def contains(p: Point): Boolean;
  def position(l: Line): LinePosition.Value;

  def position(f: Figure): FigurePosition.Value;

  def seeFrom(p: Point): (Array[Line], Segment);

  protected def produceFigure = this
}