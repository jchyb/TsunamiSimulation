package SimElements

import Utils.Vector2

import scala.collection.mutable

class Water(val particleSize: Double) {
  // Contains water particle data
  private val waterMap: mutable.HashMap[Vector2[Int], WaterParticle] = mutable.HashMap()

  // Apply water physics
  def update(deltaTime: Double): Unit = {
    for (v <- waterMap.values) {
      v.update(deltaTime)
    }
  }

  //TODO Apply wind

  //TODO Apply collision (after other objects and movement)

  def initiateWave(position: Vector2[Int], strength: Double): Unit = {
    val x = position.x
    val y = position.y

    for (i <- -1 to 1; j <- -1 to 1) {
      val newPosition = new Vector2(x + i, y + j)
      val newForce = new Vector2(i, j)
      if (newForce.length() != 0) {
        val particle = new WaterParticle(newPosition, new Vector2(i * strength, j * strength), 10, 1) //TODO placeholder values

        val particleMaybe = waterMap.get(newPosition)
        particleMaybe match {
          case Some(particleIn) => waterMap.addOne(newPosition, particle + particleIn)
          case None => waterMap.addOne(newPosition, particle)
        }
      }
    }
  }

  //def toList() : List[WaterParticle] = List(waterMap.values)//TODO fix

}
