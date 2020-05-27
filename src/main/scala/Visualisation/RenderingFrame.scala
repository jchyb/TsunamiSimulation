package Visualisation

import java.awt.Color

import SimElements._

import scala.swing.{BoxPanel, Dimension, Frame, Graphics2D, Orientation, Panel}

class RenderingFrame(private val simulationWorld : World) extends Frame with Receiver {

  private var entityIterable : Iterable[WorldEntity] = Iterable[WorldEntity]()
  title = "Visualisation"
  resizable = false
  contents = new BoxPanel(Orientation.Vertical){
    contents += new Panel {
      preferredSize = new Dimension(700, 400)
      focusable = true
      override def paint(g: Graphics2D) : Unit = draw(g)
    }
    open()
  }

  def draw(g: Graphics2D) : Unit = {
    //draw shore
    g.setColor(Color.ORANGE)
    g.fillRect(-200*2  + size.width/2,-100*2  + size.height/2,100*2  ,200*2)

    if(entityIterable.nonEmpty) {
      for (entity <- entityIterable) entity match {
        case WaterParticle(position, force, height) => {
          g.setColor(new Color(0,0,255, math.min((height*500).toInt, 255)))
          g.fillRect(position.x*2 + size.width/2, position.y*2 + size.height/2, 2, 2)
        }
        case Breakwater(position, radius, height) => {
          g.setColor(Color.BLACK)
          g.fillOval((position.x-radius).toInt*2 + size.width/2, (position.y-radius).toInt*2 + size.height/2, radius.toInt*4, radius.toInt*4)
        }
      }
    }
  }

  override def receive(iterable: Iterable[WorldEntity]): Unit = {
    this.entityIterable = iterable
    repaint()
  }
  override def closeOperation(): Unit = {
    visible = false
  }
  pack()
  centerOnScreen()
}
