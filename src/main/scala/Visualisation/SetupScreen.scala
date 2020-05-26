package Visualisation

import java.awt.Dimension

import SimElements.World
import Utils.{Logger, Vector2}

import scala.swing._
import scala.swing.event.ValueChanged

object SetupScreen {

  private var waveStrength : Double = 50
  private var windStrength : Double = 50
  private var windImpact: Double = 0.001  //  TODO: Input windImpact
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
    maximumSize = new Dimension(100,100)
    resizable = false

    contents = new BoxPanel(Orientation.Vertical) {
      //border = (TitledBorder(EtchedBorder, "Radio Buttons"), EmptyBorder(5,5,5,10))
      val settingsLabel = new Label("Wave settings:")
      contents += settingsLabel

      contents += new FlowPanel() {

        val label: Label = new Label("Force: 50")
        label.minimumSize = new swing.Dimension(30,30)
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
          label.minimumSize = new swing.Dimension(30,30)
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
          world = new World(100, new RenderingFrame(world))
          world.setWave(wavePosition, waveStrength/10)
          world.setWind(windDirection.normalise()*windStrength, windImpact)
          waiting = false
        }}
      }
      contents += new Button("Run with logger") {
        reactions += {case event.ButtonClicked(_) => {
          world = new World(100, new Logger())
          world.setWave(wavePosition, waveStrength/10)
          world.setWind(windDirection.normalise()*windStrength, windImpact)
          waiting = false
        }}
      }
      border = Swing.EmptyBorder(10, 10, 10, 10)
    }

    override def closeOperation(): Unit = System.exit(0)
    pack()
    centerOnScreen()
    open()
  }

}
