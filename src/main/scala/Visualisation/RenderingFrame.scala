package Visualisation

import java.awt.Color

import SimElements.{Breakwater, Receiver, WaterParticle, World, WorldEntity}

import scala.swing.{BoxPanel, Dimension, Frame, Graphics2D, Label, Orientation, Panel, Swing}

class RenderingFrame(private val simulationWorld : World) extends Frame with Receiver {

  private var entityIterable : Iterable[WorldEntity] = Iterable[WorldEntity]()
  private var header = new Label("Step 0/")

  contents = new BoxPanel(Orientation.Vertical){
    contents += header
    contents += new Panel {
      preferredSize = new Dimension(700, 400)
      focusable = true
      listenTo(keys)
      border=Swing.EtchedBorder(Swing.Lowered)
      override def paint(g: Graphics2D) : Unit = draw(g)
    }
    pack()
    open()
  }

  def draw(g: Graphics2D) : Unit = {

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

  override def receive(list: Iterable[WorldEntity]): Unit = {
    this.entityIterable = list
    header.text = "Objects: "+ list.size.toString
    repaint()
  }
  override def closeOperation(): Unit = {
    visible = false
  }
  centerOnScreen()
}
