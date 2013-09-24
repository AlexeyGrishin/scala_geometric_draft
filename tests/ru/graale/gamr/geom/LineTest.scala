package ru.graale.gamr.geom

import org.scalatest.FunSuite

class LineTest extends FunSuite {

  test("position of start/end points") {
    val point1 = Point(2,5)
    val point2 = Point(4,7)
    val line = new Line(point1, point2)
    expect(PointPosition.OnLine) {line.position(point1)}
    expect(PointPosition.OnLine) {line.position(point2)}
  }

  test("create using angle") {
    val point = Point(0,0)
    val angle = scala.math.Pi/4;
    val line = new Line(point, angle)
    val line2 = new Line(point, Point(2,2))
    expect(PointPosition.OnLine) { line.position(Point(2,2))}
  }

  test("position of middle point") {
    val line = new Line(Point(5,5), Point(2,2))
    expect(PointPosition.OnLine) {line.position(Point(3,3))}
  }

  test("position of points outside specified segment") {
    val line = new Line(Point(5,5), Point(2,2))
    expect(PointPosition.OnLine) {line.position(Point(13,13))}
    expect(PointPosition.OnLine) {line.position(Point(1,1))}
  }

  test("position of points under line") {
    val line = new Line(Point(5,5), Point(2,2))
    val position = line.position(Point(2,0))
    assert(position != PointPosition.OnLine, "point 2,0 is marked as on line (2,2)-(5,5)")
  }

  test("position of points over line") {
    val line = new Line(Point(5,5), Point(2,2))
    val position = line.position(Point(2,3))
    assert(position != PointPosition.OnLine, "point 2,3 is marked as on line (2,2)-(5,5)")
  }

  test("orient") {
    val line = new Line(Point(5,5), Point(2,2))
    val over = Point(2,3)
    val under = Point(2,0)
    line.orient(over, PointPosition.Inside)
    expect(PointPosition.Inside) {line.position(over)}
    expect(PointPosition.Outside) {line.position(under)}
    line.orient(over, PointPosition.Outside)
    expect(PointPosition.Inside) {line.position(under)}
    expect(PointPosition.Outside) {line.position(over)}
  }

  test("contains") {
    val line = new Line(Point(5,5), Point(2,2))
    expect(true) {line.contains(Point(3,3))}
  }

  test("not contains") {
    val line = new Line(Point(5,5), Point(2,2))
    expect(false) {line.contains(Point(3,3.1))}
  }

}