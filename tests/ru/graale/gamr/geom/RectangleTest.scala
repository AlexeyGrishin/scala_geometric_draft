package ru.graale.gamr.geom

import org.scalatest.FunSuite

class RectangleTest extends FunSuite {

  test("normal points order") {
    val rec = new Rectangle(Point(1,2), Point(3,4))
    expect((1,2,3,4)) {(rec.left, rec.top, rec.right, rec.bottom)}
  }

  test("reverse points order") {
    val rec = new Rectangle(Point(3,4), Point(1,2))
    expect((1,2,3,4)) {(rec.left, rec.top, rec.right, rec.bottom)}
  }

  test("width") {
    expect(10) {new Rectangle(Point(10, 2), Point(20, 3)).width}
    expect(2) {new Rectangle(Point(12, 2), Point(10, 3)).width}
  }

  test("height") {
    expect(1) {new Rectangle(Point(10, 2), Point(20, 3)).height}
    expect(3) {new Rectangle(Point(12, 4), Point(2, 1)).height}
  }

  test("contains inside") {
    assert(new Rectangle(1,2,3,4).contains(Point(2,3)))
  }

  test("contains on edges") {
    assert(new Rectangle(1,2,3,4).contains(Point(1, 3)))
    assert(new Rectangle(1,2,3,4).contains(Point(3, 3)))
    assert(new Rectangle(1,2,3,4).contains(Point(2, 2)))
    assert(new Rectangle(1,2,3,4).contains(Point(2, 4)))
  }

  test("contains on corners") {
    assert(new Rectangle(1,2,3,4).contains(Point(1, 2)))
    assert(new Rectangle(1,2,3,4).contains(Point(3, 4)))
    assert(new Rectangle(1,2,3,4).contains(Point(3, 2)))
    assert(new Rectangle(1,2,3,4).contains(Point(1, 4)))
  }

  test("not contains - outside") {
    assert(!new Rectangle(1,2,3,4).contains(Point(0.9, 3)))
    assert(!new Rectangle(1,2,3,4).contains(Point(3.1, 3)))
    assert(!new Rectangle(1,2,3,4).contains(Point(2, 1.9)))
    assert(!new Rectangle(1,2,3,4).contains(Point(2, 4.1)))
  }

}