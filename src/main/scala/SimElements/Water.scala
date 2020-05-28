package SimElements

import Utils.Vector2

import scala.collection.mutable

class Water() {

  private val minHeight = 0.01

  // Contains water particle data
  private val waterMap: mutable.HashMap[Vector2[Int], WaterParticle] = mutable.HashMap()

  // Apply water physics
  def update(deltaTime: Double, wind: Wind,
             breakwatersMap : mutable.HashMap[Vector2[Int],Breakwater], shore :Shore): Unit = {
    var list: List[WaterParticle] = waterMap.values.filter(_.height>minHeight).toList.flatMap(_.update(deltaTime))

    list = list.map(wind(_))

    list = list.map(shore(_))

    list = applyCollision(list, breakwatersMap)

    waterMap.clear()
    list.foreach(particle => waterMap.get(particle.position) match {
        case Some(particleIn) => waterMap.addOne(particle.position, particle + particleIn)
        case None => waterMap.addOne(particle.position, particle)})
  }

  def applyCollision(list: List[WaterParticle],
                     breakwatersMap : mutable.HashMap[Vector2[Int],Breakwater]): List[WaterParticle] = {
    var res: List[WaterParticle] = List()
    list.foreach(particle => breakwatersMap.get(particle.position) match {
      case Some(breakwater) => res = res.concat(breakwater.collide(particle))
      case None => res = particle::res})
    res
  }

  def initiateWave(position: Vector2[Int], strength: Double): Unit = {

    val x = position.x
    val y = position.y

    for (j <- -50 to 50) {
      val newPosition = Vector2(x, y + j)
      val newForce = Vector2(0, j)
      if (newForce.length() != 0) {
        val particle = WaterParticle(newPosition, Vector2(-1 * strength, 0 * strength), 10)

        waterMap.get(newPosition) match {
          case Some(particleIn) => waterMap.addOne(newPosition, particle + particleIn)
          case None => waterMap.addOne(newPosition, particle)
        }
      }
    }
  }

  // Returns iterable of WaterParticles
  def toIterable : Iterable[WaterParticle] = waterMap.values
}
