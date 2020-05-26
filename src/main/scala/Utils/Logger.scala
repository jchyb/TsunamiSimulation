package Utils

import SimElements.{Receiver, WaterParticle, WorldEntity}

class Logger extends Receiver{
  override def receive(list: Iterable[WorldEntity]): Unit = {
    for(i <- list){
      i match {
        case WaterParticle(position, force, height)
          => println("W, position: " + position.toString
                     + ", force: " + force.toString + ", height: " + height.toString)
      }
    }
  }
}
