package ru.graale.gamr.geom

import org.scalatest.FunSuite
import scala.math._
import org.scalatest.matchers.ShouldMatchers
import ru.graale.gamr.geom.WithEpsilon._;
;

class GeomTest extends FunSuite with ShouldMatchers {

  test("distance between points (1,0) and (2,0) shall be 1") {
    expect(1) { Geom.distance(Point(1,0), Point(2,0))}
  }

  test("distance between points (1,1) and (1,3.5) shall be 2.5") {
    expect(2.5) { Geom.distance(Point(1,1), Point(1,3.5))}
  }

  test("intersect") {
    expect(Option(Point(0, 0))) {
      Geom.intersect(
        new Line(Point(-1, -1), Point(1,1)),
        new Line(Point(-1, 1), Point(1,-1))
      )
    }
  }

  test("no intersect") {
    expect(None) {
      Geom.intersect(
        new Line(Point(-1, 0), Point(-1, 1)),
        new Line(Point(1, 0), Point(1, 1))
      )
    }

  }

  test("point using angle - from 0,0") {
    val point = Point(0, 0);

    expect(Point(1,0), Geom.point(point, 0))
    expect(Point(0,1), Geom.point(point, Pi/2))
    expect(Point(-1,0), Geom.point(point, Pi))
    expect(Point(0,-1), Geom.point(point, -Pi/2))
  }

  test("point using angle - from 1,1") {
    val point = Point(1, 1);

    expect(Point(2,1), Geom.point(point, 0))
    expect(Point(1,2), Geom.point(point, Pi/2))
    expect(Point(0,1), Geom.point(point, Pi))
    expect(Point(1,0), Geom.point(point, -Pi/2))
  }

  test("angleBetween") {
    val line1 = new Line(Point(0,0), Point(10, 0))
    val line2 = new Line(Point(0,0), Point(10, 10))
    expect(cos(Pi/4)) {Geom.cosBetween(line1, line2)}
  }

  def expect(r: Double) (actual : scala.Double) {
    assert(r ~ actual, "Expected " + r + ", but got " + actual)
  }

  def expect(p1: Point, p2: Point) {
    val D = 0.0001;
    if (abs(p2.x - p1.x) > D || abs(p2.y - p1.y) > D) {
      //fail with meaningful message
      expect(p1) {p2}
    }
  }

}