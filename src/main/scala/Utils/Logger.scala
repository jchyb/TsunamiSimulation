package Utils

import SimElements.{Receiver, WaterParticle, WorldEntity}

class Logger extends Receiver{
  var received : Int = 0
  override def receive(list: Iterable[WorldEntity]): Unit = {
    received += 1
    println("Step: " + received.toString)
    for(i <- list){
      i match {
        case WaterParticle(position, force, height)
          => println("W, position: " + position.toString
                     + ", force: " + force.toString + ", height: " + height.toString)
      }
    }
  }
}
