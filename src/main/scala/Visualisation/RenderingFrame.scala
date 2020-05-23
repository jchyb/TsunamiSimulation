package Visualisation

import java.awt.Color

import SimElements.{Receiver, World, WorldEntity}

import scala.swing.{BoxPanel, Dimension, Frame, Graphics2D, Label, Orientation, Panel}

class RenderingFrame(private val simulationWorld : World) extends Frame with Receiver {

  var entityIterable : Iterable[WorldEntity] = _

  contents = new BoxPanel(Orientation.Vertical){
    contents += new Label("Cycle 0/")
    contents += new Panel {
      preferredSize = new Dimension(700, 400)
      focusable = true
      listenTo(keys)

      override def paint(g: Graphics2D) : Unit = {
        g.setColor(Color.BLUE)
        g.fillRect(0, 0, size.width, size.height)
        repaint()
      }
    }
    pack()
    centerOnScreen()
    open()
  }

  override def receive(list: Iterable[WorldEntity]): Unit = {
    this.entityIterable = list
    repaint()
  }


}
