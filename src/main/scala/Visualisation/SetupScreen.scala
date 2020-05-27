package Visualisation

import java.awt.Dimension

import SimElements.World
import Utils.{Logger, Vector2}

import scala.swing._
import scala.swing.event.ValueChanged

object SetupScreen {

  private var waveStrength : Double = 50
  private var windStrength : Double = 50
  private var steps : Double = 50
  private var windImpact: Double = 0.001  //  TODO: Input windImpact
  private var wavePosition : Vector2[Int] = new Vector2[Int](0,0)
  private var windDirection: Vector2[Double] = new Vector2[Double](1, 100) // TODO: Input wind direction
  private var waiting : Boolean = true
  private var world : World = _
  private var skip : Int = 1 // skip (1/skip) steps

  def main(args: Array[String]): Unit = {
    while(waiting) Thread.sleep(1000)
    println("ok")
    world.run(skip)
  }

  new Frame {
    title = "Simulation"
    maximumSize = new Dimension(100,100)
    resizable = false

    contents = new BoxPanel(Orientation.Vertical) {
      //border = (TitledBorder(EtchedBorder, "Radio Buttons"), EmptyBorder(5,5,5,10))


      contents += new GridBagPanel {
        def constraints(x: Int, y: Int,
                        gridwidth: Int = 1, gridheight: Int = 1,
                        weightx: Double = 0.0, weighty: Double = 0.0,
                        fill: GridBagPanel.Fill.Value = GridBagPanel.Fill.None)
        : Constraints = {
          val c = new Constraints
          c.gridx = x
          c.gridy = y
          c.gridwidth = gridwidth
          c.gridheight = gridheight
          c.weightx = weightx
          c.weighty = weighty
          c.fill = fill
          c
        }
        val windLabel : Label = new Label("Wind: 50")
        val forceLabel : Label = new Label("Force: 50")

        add(new Label("Wave Settings: "), constraints(0,0,gridwidth = 2))
        add(forceLabel, constraints(0, 1, fill=GridBagPanel.Fill.Both))
        add(windLabel, constraints(1, 1))
        add(new Slider() {
          title = "Simulation"
          reactions += {
            case ValueChanged(_) => {
              waveStrength = this.value
              forceLabel.text = "Force: " + this.value.toString
            }
          }
        }, constraints(0, 2))

        add(new Slider() {
          reactions += {
            case ValueChanged(_) => {
              windStrength = this.value
              windLabel.text = "Wind: " + this.value.toString
            }
          }
        }, constraints(1, 2))

        add(new Slider() {
          reactions += {
            case ValueChanged(_) => {
              windStrength = this.value
              windLabel.text = "Wind: " + this.value.toString
            }
          }
        }, constraints(0, 3, gridwidth = 2))

        add(new Button("Run with visualisation") {
          reactions += {case event.ButtonClicked(_) => {
            world = new World(100, new RenderingFrame(world))
            world.initBreakwaters()
            world.setWind(windDirection.normalise()*windStrength, windImpact)
            world.setWave(wavePosition, waveStrength/10)
            waiting = false
          }}
        }, constraints(0,4,gridwidth = 2))

        add(new Button("Run with logger") {
          reactions += {case event.ButtonClicked(_) => {
            world = new World(100, new Logger())
            world.initBreakwaters()
            world.setWind(windDirection.normalise()*windStrength, windImpact)
            world.setWave(wavePosition, waveStrength/10)
            waiting = false
          }}
        }, constraints(0,5,gridwidth = 2))
      }
      border = Swing.EmptyBorder(10, 10, 10, 10)
    }

    override def closeOperation(): Unit = System.exit(0)
    pack()
    centerOnScreen()
    open()
  }

}
