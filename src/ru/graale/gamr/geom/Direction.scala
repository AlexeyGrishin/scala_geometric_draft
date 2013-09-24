package ru.graale.gamr.geom

abstract class Direction {
  def swapIfClockwise[T](d1: T, d2: T): (T, T) = (d1, d2)
  def opposite: Direction;
  def unary_- : Direction = opposite;
}

object Clockwise extends Direction {
  override def swapIfClockwise[T](d1: T, d2: T) = (d2, d1)
  def opposite = CounterClockwise;
}
object CounterClockwise extends Direction {
  def opposite = Clockwise;
}
