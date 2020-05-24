package Visualisation

import java.awt.Color

import SimElements.{Receiver, WaterParticle, World, WorldEntity}

import scala.swing.{BoxPanel, Dimension, Frame, Graphics2D, Label, Orientation, Panel}

class RenderingFrame(private val simulationWorld : World) extends Frame with Receiver {

  private var entityIterable : Iterable[WorldEntity] = Iterable[WorldEntity]()
  private var header = new Label("Cycle 0/")

  contents = new BoxPanel(Orientation.Vertical){
    contents += header

    contents += new Panel {
      preferredSize = new Dimension(700, 400)
      focusable = true
      listenTo(keys)

      override def paint(g: Graphics2D) : Unit = {
        g.setColor(Color.BLUE)

        if(entityIterable.nonEmpty) {
          for (entity <- entityIterable) entity match {
              case WaterParticle(position, force, height, length) => {
                //g.setColor(new Color(0,0,255, math.min((height*2000).toInt, 255)))
                g.setColor(Color.BLACK)
                g.fillRect(position.x + size.width/2, position.y + size.height/2, 1, 1)
              }
            }

        }
        //
      }
    }
    pack()
    centerOnScreen()
    open()
  }

  override def receive(list: Iterable[WorldEntity]): Unit = {
    this.entityIterable = list
    header.text = "Objects: ".concat(list.size.toString)
    repaint()
    print("ni")
  }


}
