package ru.graale.gamr.geom

import figures._
import org.scalatest.FunSuite;
import org.mockito.Mockito._;

class CompositeSegmentTest extends FunSuite {

  test("empty") {
    intercept[IllegalArgumentException] {new CompositeSegment(List())}
  }

  test("single segment") {
    val segment: Segment = mock(classOf[Segment])
    when(segment.startPoint).thenReturn(Point(1,1))
    when(segment.endPoint).thenReturn(Point(2,2))
    when(segment.length).thenReturn(5)
    val composite = new CompositeSegment(List(segment))

    expect(Point(1,1)) { composite.startPoint}
    expect(Point(2,2)) { composite.endPoint}
    expect(5) { composite.length}
  }

  test("two segments") {
    val segment1: Segment = mock(classOf[Segment])
    val segment2: Segment = mock(classOf[Segment])
    when(segment1.startPoint).thenReturn(Point(1,1))
    when(segment1.endPoint).thenReturn(Point(2,2))
    when(segment1.length).thenReturn(5)
    when(segment2.startPoint).thenReturn(Point(2,2))
    when(segment2.endPoint).thenReturn(Point(4,4))
    when(segment2.length).thenReturn(1)
    val composite = new CompositeSegment(List(segment1, segment2))

    expect(Point(1,1)) { composite.startPoint}
    expect(Point(4,4)) { composite.endPoint}
    expect(6) { composite.length}
  }

  test("two segments - unordered") {
    val segment1: Segment = mock(classOf[Segment])
    val segment2: Segment = mock(classOf[Segment])
    when(segment1.startPoint).thenReturn(Point(2,2))
    when(segment1.endPoint).thenReturn(Point(4,4))
    when(segment2.startPoint).thenReturn(Point(1,1))
    when(segment2.endPoint).thenReturn(Point(2,2))
    val composite = new CompositeSegment(List(segment1, segment2))

    expect(Point(1,1)) { composite.startPoint}
    expect(Point(4,4)) { composite.endPoint}
  }

  test("two segments - no linked") {
    val segment1: Segment = mock(classOf[Segment])
    val segment2: Segment = mock(classOf[Segment])
    when(segment1.startPoint).thenReturn(Point(1,1))
    when(segment1.endPoint).thenReturn(Point(2,2))
    when(segment2.startPoint).thenReturn(Point(3,3))
    when(segment2.endPoint).thenReturn(Point(4,4))
    intercept[IllegalArgumentException] {new CompositeSegment(List(segment1, segment2))}
  }

  test("invert") {
    val segment = new CompositeSegment(
      List(
        new StraightSegment(Point(1,1), Point(2,2)),
        new StraightSegment(Point(2,2), Point(4,2)),
        new StraightSegment(Point(4,2), Point(5,1))
      )
    )
    val inverted = segment.invert
    expect(Point(5,1)) {inverted.startPoint}
    expect(Point(1,1)) {inverted.endPoint}
  }


  test("split by") {
    val segment = new CompositeSegment(
      List(
        new StraightSegment(Point(1,1), Point(2,2)),
        new StraightSegment(Point(2,2), Point(4,2)),
        new StraightSegment(Point(4,2), Point(5,1))
      )
    )

    val arr = segment.splitBy(List(Point(3.5, 2), Point(2.5, 2))).toArray
    expect(3) {arr.length}
    expect((Point(1,1), Point(2.5, 2))) {(arr(0).startPoint, arr(0).endPoint)}
    expect((Point(2.5,2), Point(3.5, 2))) {(arr(1).startPoint, arr(1).endPoint)}
    expect((Point(3.5,2), Point(5, 1))) {(arr(2).startPoint, arr(2).endPoint)}
    expect(segment.length) {arr.elements.toList.map(_.length).reduce(_ + _)}
  }

  test("split by nothing") {
    val segment = new CompositeSegment(
      List(
        new StraightSegment(Point(1,1), Point(2,2)),
        new StraightSegment(Point(2,2), Point(4,2)),
        new StraightSegment(Point(4,2), Point(5,1))
      )
    )

    val arr = segment.splitBy(List()).toArray
    expect(1) {arr.length}
    expect((Point(1,1), Point(5, 1))) {(arr(0).startPoint, arr(0).endPoint)}
    expect(segment.length) {arr.elements.toList.map(_.length).reduce(_ + _)}
  }

  test("split by corner") {
    val segment = new CompositeSegment(
      List(
        new StraightSegment(Point(1,1), Point(2,2)),
        new StraightSegment(Point(2,2), Point(4,2)),
        new StraightSegment(Point(4,2), Point(5,1))
      )
    )

    val arr = segment.splitBy(List(Point(2,2))).toArray
    expect(2) {arr.length}
    expect((Point(1,1), Point(2, 2))) {(arr(0).startPoint, arr(0).endPoint)}
    expect((Point(2,2), Point(5, 1))) {(arr(1).startPoint, arr(1).endPoint)}
    expect(segment.length) {arr.elements.toList.map(_.length).reduce(_ + _)}

  }
}