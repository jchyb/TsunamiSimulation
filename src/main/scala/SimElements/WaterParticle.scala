package SimElements

import Utils.Vector2

case class WaterParticle(val position: Vector2[Int], val force: Vector2[Double], val height: Double, val length: Double) {
  // force - where water of the particle is going
  // height - amount of water in the particle
  // length - const for every particle, width/height of represented area


  def update(deltaTime: Double): List[WaterParticle] = {
    /* Divides and moves the particle according to force strength and direction.
    Returns a queue of newly created particles. */

    var newParticles = List[WaterParticle]()
    // TODO improve range depending on distance between particles (ja to zrobie)
    val centralPosition = new Vector2[Int]((position.x + (force.x * deltaTime)).asInstanceOf[Int],
      (position.y + (force.y * deltaTime)).asInstanceOf[Int])

    val maxLevel = 1
    for (i <- -maxLevel to maxLevel; j <- -maxLevel to maxLevel) {

      val level = math.max(math.abs(i), Math.abs(j))
      val elementsInLevel = if(level == 0) 1 else 8 + 4 * level
      val offset = Vector2[Int](i, j)
      val newPosition = centralPosition + offset

      newParticles = newParticles :+ WaterParticle(newPosition, force,
        height * math.pow(1.0/2, level+1) / elementsInLevel, length)
    }

    newParticles = newParticles :+ WaterParticle(centralPosition, force,
      height * math.pow(1.0/2, maxLevel+1), length)

    newParticles
  }

  def +(other: WaterParticle): WaterParticle = {
    val newForce = (this.force * this.height + other.force * other.height) / (this.height + other.height)
    WaterParticle(position, newForce, this.height + other.height, length)
  }
}
