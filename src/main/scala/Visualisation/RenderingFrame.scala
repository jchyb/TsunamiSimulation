package Visualisation

import SimElements.World

import scala.swing.{BoxPanel, Frame, Label, Orientation}

class RenderingFrame(private val simulationWorld : World) extends Frame{

  contents = new BoxPanel(Orientation.Vertical){
    contents += new Label("Cycle 0/")
    pack()
    centerOnScreen()
    open()
  }
}
