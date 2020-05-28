package Visualisation

import java.awt.Dimension

import SimElements.{Receiver, WaterParticle, World}
import Utils.{Logger, Vector2}

import scala.swing._
import scala.swing.event.ValueChanged

object SetupFrame {

  private var waveStrength : Double = 50
  private var windStrength : Double = 50
  private var steps : Int = 50
  private val windImpact: Double = 0.001
  private val wavePosition : Vector2[Int] = new Vector2[Int](50,0)
  private var windDirection: Vector2[Double] = new Vector2[Double](1, 100)
  private var waiting : Boolean = true
  private var world : World = _
  private val shoreStart: Int = -100 //-200
  private val shoreSteepness: Double = 0.01 //0.1
  private val skip : Int = 1
  private var breakWaterSize : Double = 5
  private var breakWaterAmount : Int = 5

  def main(args: Array[String]): Unit = {
    while(true) {
      while (waiting) Thread.sleep(1000)
      waiting = true
      world.run(skip)
    }
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
        val stepLabel : Label = new Label("Steps: 50")
        val breakWaterSizeLabel : Label = new Label("Breakwater Size: " + breakWaterSize.toString)
        val breakWaterAmountLabel : Label = new Label("Breakwater Amount: " + breakWaterAmount.toString)
        val windDirLabel : Label = new Label("Wind direction: " + windDirection.toString)

        add(new Label("Wave Settings: "), constraints(0,0,gridwidth = 2))
        add(forceLabel, constraints(0, 1, fill=GridBagPanel.Fill.Both))
        add(windLabel, constraints(1, 1))
        add(new Slider() {
          title = "Simulation"
          reactions += {
            case ValueChanged(_) =>
              waveStrength = this.value
              forceLabel.text = "Force: " + this.value.toString
          }
        }, constraints(0, 2))

        add(new Slider() {
          reactions += {
            case ValueChanged(_) =>
              windStrength = this.value
              windLabel.text = "Wind: " + this.value.toString
          }
        }, constraints(1, 2))

        add(stepLabel,constraints(0,3,gridwidth = 2))
        add(new Slider() {
          reactions += {
            case ValueChanged(_) =>
              steps = this.value
              stepLabel.text = "Steps: " + this.value.toString
          }
        }, constraints(0, 4, gridwidth = 2))

        add(windDirLabel, constraints(0,5,gridwidth = 2))
        add(new TextField(windDirection.x.toString, 10){
          reactions += {
            case ValueChanged(_) =>
              windDirection = Vector2(this.text.toDoubleOption.getOrElse(windDirection.x), windDirection.y)
              windDirLabel.text = "Wind direction: " + windDirection.toString
          }
        }, constraints(0, 6))
        add(new TextField(windDirection.y.toString, 10){
          reactions += {
            case ValueChanged(_) =>
              windDirection = Vector2(windDirection.x, this.text.toDoubleOption.getOrElse(windDirection.y))
              windDirLabel.text = "Wind direction: " + windDirection.toString
          }
        }, constraints(1, 6))

        add(breakWaterSizeLabel,constraints(0,7))
        add(new Slider() {
          reactions += {
            case ValueChanged(_) =>
              breakWaterSize = (this.value)/10.0
              breakWaterSizeLabel.text = "Breakwater Size: " + breakWaterSize.toString
          }
        }, constraints(0, 8))

        add(breakWaterAmountLabel,constraints(1,7))
        add(new Slider() {
          reactions += {
            case ValueChanged(_) =>
              breakWaterAmount = this.value.toInt/10
              breakWaterAmountLabel.text = "Breakwater Amount: " + breakWaterAmount.toString
          }
        }, constraints(1, 8, gridwidth = 1))

        add(new Button("Run with visualisation") {
          reactions += {case event.ButtonClicked(_) =>
            makeWorld(new RenderingFrame(world))
            waiting = false
          }
        }, constraints(0,9,gridwidth = 2))

        add(new Button("Run with log") {
          reactions += {case event.ButtonClicked(_) =>
            makeWorld(new Logger())
            waiting = false
          }
        }, constraints(0,10,gridwidth = 2))
      }
      border = Swing.EmptyBorder(10, 10, 10, 10)
    }

    override def closeOperation(): Unit = System.exit(0)
    pack()
    centerOnScreen()
    open()
  }

  def makeWorld(receiver : Receiver) : World = {
    if(world != null) world.stop()
    world = new World(steps, receiver)
    world.initShore(e => if (e.position.x>shoreStart) e else
      WaterParticle(e.position, e.force + Vector2(1, 0)*(shoreStart-e.position.x)*shoreSteepness, e.height))

    val breakwaterSeq = for(i <- 1 to breakWaterAmount)
      yield((Vector2[Int](-50,200*i/(breakWaterAmount+1)-100), breakWaterSize, 10.0))
    world.initBreakwaters(breakwaterSeq.toList)
    world.setWind(windDirection.normalise()*windStrength, windImpact)
    world.setWave(wavePosition, waveStrength/10)
    world
  }

}
