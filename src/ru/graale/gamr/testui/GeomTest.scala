package ru.graale.gamr.testui

import ru.graale.gamr.geom._
import figures._
import javax.swing.{SwingUtilities, JFrame}
import scala.swing.{MainFrame, SimpleSwingApplication, SwingApplication}
import java.awt.{Dimension, Color, Graphics2D, Graphics}
import scala.swing.event._
import scala.swing.Swing._
import ru.graale.gamr.geom.WithEpsilon._

object GeomTest extends SimpleSwingApplication {

  def top = new MainFrame {
    val panel = new Panel()
    contents = panel
    panel.focusable = true
    listenTo(panel.mouse.clicks, panel.keys)
    reactions += {
      case KeyPressed(_, Key.Left, _, _) =>
        observer.rotate(scala.math.toRadians(-5))
        updateSegments()
        repaint()
      case KeyPressed(_, Key.Right, _, _) =>
        observer.rotate(scala.math.toRadians(5))
        updateSegments()
        repaint()
      case KeyPressed(_, Key.Up, _, _) =>
        observer.move(5, {!world.inFigure(_)})
        updateSegments()
        repaint()
      case e: KeyPressed =>
        updateSegments()
        repaint()
    }
    size = new Dimension(300, 300)


  }

  val observer = new Observer(Point(155, 85), 2.4434609527920643, scala.math.Pi / 2)
  val world = new GeometricWorld
  world += Geom.rectangle(Point(40, 40), Point(50, 50))
  world += Geom.rectangle(Point(20, 20), Point(200, 30))
  world += Geom.rectangle(Point(20,30), Point(30, 250))
  world += Geom.triangle(Point(40, 120), Point(55, 130), Point(45, 200))
  world += Geom.circle(Point(150, 50), 18, Quality(0.5))
  world += new PolygonialFigure(List(Point(50, 80), Point(70, 90), Point(65, 105), Point(42, 87), Point(40, 81)))

  var visibleSegments: List[Segment] = updateSegments()

  private def updateSegments() = {
    visibleSegments = observer.view(world.figures)
    visibleSegments
  }

  class Panel extends scala.swing.Panel {


    override def paintComponent(g2d: Graphics2D) {
      g2d.clearRect(0, 0, size.width, size.height)
      //draw world

      g2d.setColor(Color.BLACK)
      world.figures.foreach(x => {
        x match {
          case polygon: PolygonialFigure => draw(g2d, polygon)
          case _ => //skip
        }
      })
      //draw observer
      g2d.setPaint(Color.BLUE)
      g2d.setColor(Color.BLUE)
      g2d.fillOval(observer.pos.x.roundInt - 5, observer.pos.y.roundInt - 5, 10, 10)
      val p = Geom.point(observer.pos, observer.getAngle, 15)
      line(g2d, observer.pos, p)
      //draw visible segments
      g2d.setColor(Color.RED)
      visibleSegments.foreach(draw(g2d, _))
      //draw observer's view angle
      g2d.setColor(new Color(128, 128, 0, 200))
      observer.viewAngles.foreach( (angle) => line(g2d, observer.pos, Geom.point(observer.pos, angle, size.width)))
    }

    def draw(g2d: Graphics2D, s: Segment) {
      s match {
        case ss: StraightSegment =>
          val color = new Color((ss.visibilityFactor(observer.pos)*64+192).toInt, 0, 0)
          g2d.setColor(color)
          line(g2d, ss.startPoint, ss.endPoint)
          //g2d.drawOval(ss.startPoint.x.roundInt-1, ss.startPoint.y.roundInt-1, 3, 3)
          //g2d.drawOval(ss.endPoint.x.roundInt-1, ss.endPoint.y.roundInt-1, 3, 3)
        case cs: CompositeSegment => cs.segments.foreach(draw(g2d, _))
        case _ => //skip
      }
    }

    def draw(g2d: Graphics2D, p: PolygonialFigure) {
      p.edges.foreach(e => line(g2d, e.startPoint, e.endPoint))
    }

    def line(g2d: Graphics2D, p: Point, p2: Point) {
      g2d.drawLine(p.x.roundInt, p.y.roundInt, p2.x.roundInt, p2.y.roundInt)
    }
  }
}

