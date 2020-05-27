package SimElements

import Utils.Vector2

case class Breakwater(private val position: Vector2[Int], private val radius: Double, private val height: Double) extends WorldEntity {
  // position of the middle, radius and height of circular breakwater
  // how collision with breakwater impacts water's force
  val modifier: Double = 0.1

  def collide(particle: WaterParticle): List[WaterParticle] = {
    var list: List[WaterParticle] = List()
    var collided = particle
    if(this.height<particle.height){
      list = WaterParticle(particle.position, particle.force, particle.height-this.height)::list
      collided = WaterParticle(particle.position, particle.force, this.height)
    }
    if (collided.position==this.position) {
      val norm: Vector2[Double] = -particle.force.normalise()
      val mov: Vector2[Int] = Vector2((norm.x.sign*1).toInt, (norm.y.sign*1).toInt)
      collided = WaterParticle(particle.position+mov, particle.force, collided.height)
    }
    val newPosition: Vector2[Int] = this.firstFree(collided)
    val newForce: Vector2[Double] = this.impact(collided)
    list = WaterParticle(newPosition, newForce, collided.height)::list
    list
  }

  def firstFree(particle: WaterParticle): Vector2[Int] = {
    val toOut: Vector2[Int] = particle.position-this.position
    this.position+(toOut.normalise()*(this.radius+1)).int()
  }

  def impact(particle: WaterParticle): Vector2[Double] = {
    val toOut: Vector2[Double] = (particle.position - this.position).double()
    val res = particle.force + (toOut.normalise()*particle.force.length()*modifier)
    if(res.length()>particle.force.length())
      res.normalise()*particle.force.length()
    else
      res
  }
}
