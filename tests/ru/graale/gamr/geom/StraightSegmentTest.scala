package ru.graale.gamr.geom

import figures._
import org.scalatest.FunSuite

class StraightSegmentTest extends FunSuite {

  test("points and length") {
    val segment = new StraightSegment(
      Point(1,1), Point(4,5)
    )
    expect(Point(1,1)) {segment.startPoint}
    expect(Point(4,5)) {segment.endPoint}
    expect(5) {segment.length}
  }

  test("center") {
    expect(Point(2,2)) { new StraightSegment(Point(1,1), Point(3,3)).center}
  }

  test("intersect line crossing the segment") {
    val segment = new StraightSegment(Point(1,1), Point(4,2))
    val line = new Line(Point(3,1), Point(2,2))
    val array = segment.intersect(line)
    expect(1) {array.length}
    expect(Point(2.5, 1.5)) {array(0)}
  }

  test("intersect line not crossing the segment, but crossing the segment's line") {
    val segment = new StraightSegment(Point(1,1), Point(4,2))
    val line = new Line(Point(-3,-1), Point(-2,-2))
    val array = segment.intersect(line)
    expect(0) {array.length}
  }

  test("split by") {
    val segment = new StraightSegment(Point(1,0), Point(10, 0))
    val splitted = segment.splitBy(List(Point(2,0), Point(4,0))).toArray
    expect(3) {splitted.length}
    expect((Point(1,0), Point(2,0))) { (splitted(0).startPoint, splitted(0).endPoint) }
    expect((Point(2,0), Point(4,0))) { (splitted(1).startPoint, splitted(1).endPoint) }
    expect((Point(4,0), Point(10,0))) { (splitted(2).startPoint, splitted(2).endPoint) }
  }

  test("split by unordered points") {
    val segment = new StraightSegment(Point(10,0), Point(1, 0))
    val splitted = segment.splitBy(List(Point(2,0), Point(4,0))).toArray
    expect(3) {splitted.length}
    expect((Point(10,0), Point(4,0))) { (splitted(0).startPoint, splitted(0).endPoint) }
    expect((Point(4,0), Point(2,0))) { (splitted(1).startPoint, splitted(1).endPoint) }
    expect((Point(2,0), Point(1,0))) { (splitted(2).startPoint, splitted(2).endPoint) }

  }

  test("split by points outside segment") {
    val segment = new StraightSegment(Point(10,0), Point(1, 0))
    intercept[IllegalArgumentException] {segment.splitBy(List(Point(0, 0)))}
    intercept[IllegalArgumentException] {segment.splitBy(List(Point(11, 0)))}
  }

  test("position by line which crossing line") {
    val segment = new StraightSegment(Point(1,1), Point(4,2))
    val line = new Line(Point(3,1), Point(2,2))
    expect(PointPosition.OnLine) { segment.positionFromLine(line)}
  }

  test("position by line which no crossing line") {
    val segment = new StraightSegment(Point(1,1), Point(4,2))
    val line = new Line(Point(-3,-1), Point(-2,-2))
    line.orient(Point(1,1), PointPosition.Inside)
    expect(PointPosition.Inside) { segment.positionFromLine(line)}
    line.orient(Point(4,2), PointPosition.Outside)
    expect(PointPosition.Outside) { segment.positionFromLine(line)}
  }

  test("position by line which tangent line") {
    val segment = new StraightSegment(Point(1,1), Point(4,2))
    val line = new Line(Point(1,1), Point(-2,-2))
    line.orient(Point(4,2), PointPosition.Inside)
    expect(PointPosition.Inside) { segment.positionFromLine(line)}
  }

}