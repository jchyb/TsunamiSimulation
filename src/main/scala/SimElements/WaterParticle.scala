package SimElements

import Utils.Vector2
import scala.collection.mutable

case class WaterParticle(position: Vector2[Int], force: Vector2[Double], height: Double, length: Double) {
  // force - where water of the particle is going
  // height - amount of water in the particle
  // length - const for every particle, width/height of represented area

  def update(deltaTime: Double): mutable.Queue[WaterParticle] = {
    /* Divides and moves the particle according to force strength and direction.
    Returns a queue of newly created particles. */
    new mutable.Queue[WaterParticle]()
  }

  def +(other: WaterParticle): WaterParticle = {
    val newForce = (this.force * this.height + other.force * other.height) / (this.height + other.height)
    WaterParticle(position, newForce, this.height + other.height, length)
  }
}
