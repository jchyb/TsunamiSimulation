package Utils

import java.io.{File, PrintWriter}

import SimElements.{Breakwater, WaterParticle, World}

object FileHandler {
  def fromFile : World = {
  //TODO
    new World(1,null)
  }

  def toFile(world : World) = {
    //TODO
    val fileObject = new File("file.txt")
    val writer = new PrintWriter(fileObject)

    val entities = world.toIterable
    for(i <- entities) i match {
      case WaterParticle(position, force, height) => writer.println("W "+position+" "+force+" "+height)
      case Breakwater(position, radius, height) => writer.println("B "+position+" "+radius+" "+height)
    }
  }
}
