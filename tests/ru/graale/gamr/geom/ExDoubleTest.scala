package ru.graale.gamr.geom

import scala.math._
import org.scalatest.FunSuite
;

class ExDoubleTest extends FunSuite {

  import ru.graale.gamr.geom.WithEpsilon._

  test("convert from double") {
    val d: Double = 3;
    val ed: ExDouble = d;
    assert(ed.value === 3)
  }

  test("convert to double") {
    val ex: ExDouble = ExDouble(5.5)
    val d = ex
    assert(d == 5.5)
  }

  test("comparison with epsilon - doubles") {
    val d1: Double = 3.9999999
    val d2: Double = 4

    assert(d1 ~ d2)
  }

  test("pow") {
    expect(2.0) {2.0 ^ 1}
    expect(4.0) {2.0 ^ 2}
  }

  test("pow for negatives") {
    expect(4.0) {-2.0 ^ 2}
    expect(-8.0) {-2.0 ^ 3}
  }

  test("less or equal with epsilon - doubles") {
    val d1: Double = 3.9999999
    val d2: Double = 3
    val big: Double = 4

    assert(d1 <~ big)
    assert(d2 <~ big)
  }

  test("more or equal with epsilon - doubles") {
    val d1: Double = 3.9999999
    val d2: Double = 3.0000001
    val big: Double = 3

    assert(d1 >~ big)
    assert(d2 >~ big)
  }

  test("another epsilon") {
    val d1: Double = 3
    val d2: Double = 3.1
    val d3: Double = 3.000001

    assert(d1 !~ d2)
    assert(d1 ~ d3)

    WithEpsilon(0.5) {
      assert(d1 ~ d2)
      assert(d1 ~ d3)
    }

    WithEpsilon(0.0000000001) {
      assert(d1 !~ d2)
      assert(d1 !~ d3)
    }
  }

}