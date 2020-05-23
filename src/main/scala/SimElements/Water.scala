package SimElements

import Utils.Vector2

import scala.collection.mutable

class Water(val particleSizeInMeters: Double) {
  // Contains water particle data
  private val waterMap: mutable.HashMap[Vector2[Int], WaterParticle] = mutable.HashMap()

  // Apply water physics
  def update(deltaTime: Double): Unit = {
    var list = List[WaterParticle]()

    for (v <- waterMap.values) {
      list = List.concat(list, v.update(deltaTime))
    }

    // apply wind
    applyWind(list)
    //apply collision
    applyCollision(list)

    waterMap.clear()
    for (particle <- list){
      waterMap.get(particle.position) match {
        case Some(particleIn) => waterMap.addOne(particle.position, particle + particleIn)
        case None => waterMap.addOne(particle.position, particle)
      }
    }
  }

  //TODO Apply wind
  def applyWind(list: List[WaterParticle]) = {
    list
  }

  //TODO Apply collision (after other objects and movement)
  def applyCollision(list: List[WaterParticle]) = {
    list
  }

  def initiateWave(position: Vector2[Int], strength: Double): Unit = {
    val x = position.x
    val y = position.y

    for (i <- -1 to 1; j <- -1 to 1) {
      val newPosition = new Vector2(x + i, y + j)
      val newForce = new Vector2(i, j)
      if (newForce.length() != 0) {
        val particle = WaterParticle(newPosition, new Vector2(i * strength, j * strength), 10, particleSizeInMeters) //TODO placeholder values

        waterMap.get(newPosition) match {
          case Some(particleIn) => waterMap.addOne(newPosition, particle + particleIn)
          case None => waterMap.addOne(newPosition, particle)
        }
      }
    }
  }

  // Returns immutable list of WaterParticles
  // def toList() : List[WaterParticle] = List(waterMap.values)//TODO fix

}
