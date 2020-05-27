package SimElements

import Utils.Vector2

case class WaterParticle(position: Vector2[Int], force: Vector2[Double], height: Double) extends WorldEntity{
  // force - where water of the particle is going
  // height - amount of water in the particle
  // length - const for every particle, width/height of represented area

  def update(deltaTime: Double=1): List[WaterParticle] = {
    /* Divides and moves the particle according to force strength and direction.
    Returns a list of newly created particles. */
    spill(deltaTime)
  }

  def spill(deltaTime: Double): List[WaterParticle] ={
    var newParticles = List[WaterParticle]()

    val centralPosition = new Vector2[Int]((position.x + (force.x * deltaTime)).toInt,
      (position.y + (force.y * deltaTime)).toInt)

    val heightDifferenceImpact = 0.05
    val maxLevel = 1
    for (i <- -maxLevel to maxLevel; j <- -maxLevel to maxLevel) {

      val level = math.max(math.abs(i), Math.abs(j))
      val elementsInLevel = if(level == 0) 1 else 8 + 4 * level
      val offset = Vector2[Int](i, j)
      val newHeight = height * math.pow(1.0/2, level+1) / elementsInLevel
      val newPosition = centralPosition + offset
      val forceChange = if (i!=0 && j!=0) offset.normalise() * heightDifferenceImpact * (height - newHeight) else Vector2[Double](0,0)

      newParticles = newParticles :+ WaterParticle(newPosition, force + forceChange, newHeight)
    }

    newParticles = newParticles :+ WaterParticle(centralPosition, force,
      height * math.pow(1.0/2, maxLevel+1))

    newParticles
  }

  def +(other: WaterParticle): WaterParticle = {
    val newForce = (this.force * this.height + other.force * other.height) / (this.height + other.height)
    WaterParticle(position, newForce, this.height + other.height)
  }
}
