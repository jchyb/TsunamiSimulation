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

    contents = new BoxPanel(Orientation.Vertical) {
      val settingsLabel = new Label("Wave settings:")
      contents += settingsLabel

      contents += new FlowPanel() {

        val label: Label = new Label("Force: 50")
        contents += label

        contents += new Slider() {
          title = "Simulation"
          reactions += {
            case ValueChanged(_) => {
              waveStrength = this.value
              label.text = "Force: " + this.value.toString
            }
          }
        }
      }
        //TODO - nie spełnia DRY - dokładnie to co wyżej
        contents += new FlowPanel(){

          val label : Label = new Label("Wind: 50")
          contents += label

          contents += new Slider() {
            title = "Simulation"
            reactions += {
              case ValueChanged(_) => {
                waveStrength = this.value
                label.text = "Wind: " + this.value.toString
              }
            }
          }

      }

      contents += new Button("Run with visualisation") {
        reactions += {case event.ButtonClicked(_) => print("read", waveStrength)}
      }
      contents += new Button("Run with logger") {
        reactions += {case event.ButtonClicked(_) => print("read", waveStrength)}
      }
      border = Swing.EmptyBorder(10, 10, 10, 10)
    }

    override def closeOperation(): Unit = System.exit(0)
    pack()
    centerOnScreen()
    open()
  }
}
