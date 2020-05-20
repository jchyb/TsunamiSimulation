package Visualisation

import scala.swing._
import scala.swing.event.ValueChanged

object SetupScreen {

  private var waveStrength : Double = 0
  private var simulationTimeInSec : Double = 1

  def main(args: Array[String]): Unit = {
    println("Start")
  }
  new Frame {
    title = "Simulation"

    contents = new FlowPanel {
      contents += new Label("Wave settings:")
      contents += new Button("Start") {
        reactions += {
          case event.ButtonClicked(_) => print("read", waveStrength)
        }
      }
      var label : Label = new Label("Value: 0.0")

      contents += new Slider() {
        title = "Simulation"
        reactions += {
          case ValueChanged(_) => {
            waveStrength = this.value
            label.text = "Value: " + this.value.toString
          }
        }
      }
      contents += label

    }
    pack()
    centerOnScreen()
    open()
  }
}
