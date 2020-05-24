package Visualisation

import SimElements.World
import Utils.Vector2

import scala.swing._
import scala.swing.event.ValueChanged

object SetupScreen {

  private var waveStrength : Double = 50
  private var windStrength : Double = 50
  private var windImpact: Double = 0.1  //  TODO: Input windImpact
  private var simulationTimeInSec : Double = 100
  private var wavePosition : Vector2[Int] = new Vector2[Int](0,0)
  private var windDirection: Vector2[Double] = new Vector2[Double](1, 100) // TODO: Input wind direction
  private var waiting : Boolean = true
  private var world : World = _

  def main(args: Array[String]): Unit = {
    while(waiting) Thread.sleep(1000)
    println("ok")
    world.run()
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
                windStrength = this.value
                label.text = "Wind: " + this.value.toString
              }
            }
          }

      }

      contents += new Button("Run with visualisation") {
        reactions += {case event.ButtonClicked(_) => {
          print("read", waveStrength, windStrength)
          world = new World(100,new RenderingFrame(world)) //TODO change
          world.setWave(wavePosition, waveStrength)
//          world.setWind(windDirection, windStrength)
          world.setWind(windDirection.normalise()*windStrength, windImpact)
          waiting = false
        }}
      }
      contents += new Button("Run with logger") {
        reactions += {case event.ButtonClicked(_) => print("read", waveStrength, windStrength)}
      }
      border = Swing.EmptyBorder(10, 10, 10, 10)
    }

    override def closeOperation(): Unit = System.exit(0)
    pack()
    centerOnScreen()
    open()
  }

}
