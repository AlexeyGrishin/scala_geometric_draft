package ru.graale.gamr.geom

import figures._
import org.scalatest.{BeforeAndAfter, FunSuite}

class TriangleTest extends FunSuite with BeforeAndAfter {

  var triangle: Figure = null

  before {
    triangle = Geom.triangle(Point(1,1), Point(4,2), Point(2,4))
  }

  test("triangle bounds") {
    expect(new Rectangle(Point(1, 1), Point(4,4))) {
      triangle.bounds
    }
  }

  test("triangle contains - points inside") {
    assert(triangle.contains(Point(2,2)))
    assert(triangle.contains(Point(3,3)))
  }

  test("triangle contains - points outside") {
    assert(!triangle.contains(Point(4,3)))
  }

  test("triangle line position - cross") {
    expect(LinePosition.Cross) {triangle.position(new Line(Point(2,1), Point(1,3)))}
  }

  test("triangle line position - tangent") {
    expect(LinePosition.TangentInside) {triangle.position(new Line(Point(1,0), Point(1,3)))}
    expect(LinePosition.TangentOutside) {triangle.position(new Line(Point(4,0), Point(4,3)))}
  }

  test("triangle line position - outside") {
    expect(LinePosition.Inside) {triangle.position(new Line(Point(0,0), Point(1,4)))}
    expect(LinePosition.Outside) {triangle.position(new Line(Point(0,0), Point(4,1)))}
  }

  test("see from - single edge") {
    val (lines, segment) = triangle.seeFrom(Point(3,1));
    assert(lines(0).contains(Point(1,1)))
    assert(lines(1).contains(Point(4,2)))
    expect((Point(1,1), Point(4,2))) {(segment.startPoint, segment.endPoint)}
    assert(!segment.contains(Point(2,4)))
  }

  test("see from - two edges") {
    val (lines, segment) = triangle.seeFrom(Point(2,10));
    assert(lines(0).contains(Point(1,1)))
    assert(lines(1).contains(Point(4,2)))
    expect((Point(4,2), Point(1,1))) {(segment.startPoint, segment.endPoint)}
    assert(segment.contains(Point(2,4)))
    assert(segment.isInstanceOf[CompositeSegment])
  }

  test("see from - last and first edges") {
    val (lines, segment) = triangle.seeFrom(Point(-1,-1));
    assert(lines(1).contains(Point(2,4)))
    assert(lines(0).contains(Point(4,2)))
    expect((Point(2,4), Point(4,2))) {(segment.startPoint, segment.endPoint)}
    assert(segment.contains(Point(1,1)))
    assert(segment.isInstanceOf[CompositeSegment])
  }

  test("intersect figure") {
    val triangle2 = Geom.triangle(Point(2,2), Point(5,4), Point(3,5))
    expect(FigurePosition.Intersect) {triangle.position(triangle2)}
    expect(FigurePosition.Intersect) {triangle2.position(triangle)}
  }

  test("intersect figure - no points inside another figure") {
    val triangle2 = Geom.triangle(Point(3,1), Point(3,5), Point(5,2))

    expect(FigurePosition.Intersect) {triangle.position(triangle2)}
    expect(FigurePosition.Intersect) {triangle2.position(triangle)}

  }

  test("intersect figure - one inside another") {
    val triangle2 = Geom.triangle(Point(0,0), Point(2,5), Point(5,2))

    expect(FigurePosition.Wrapped) {triangle.position(triangle2)}
    expect(FigurePosition.Inside) {triangle2.position(triangle)}
  }

  test("intersect figure - no intersection") {
    val triangle2 = Geom.triangle(Point(0,0), Point(1,0), Point(0,1))
    expect(FigurePosition.Outside) {triangle.position(triangle2)}
    expect(FigurePosition.Outside) {triangle2.position(triangle)}
  }

}