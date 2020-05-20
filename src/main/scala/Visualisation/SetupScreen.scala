package Visualisation

import scala.swing._

object SetupScreen {
  def main(args: Array[String]): Unit = {
    println("StartTest")
  }
  new Frame {
    title = "Hello world"

    contents = new FlowPanel {
      contents += new Label("Launch rainbows:")
      contents += new Button("Click me") {
        reactions += {
          case event.ButtonClicked(_) =>
            println("All the colours!")
        }
      }
    }
    pack()
    centerOnScreen()
    open()
  }
}
