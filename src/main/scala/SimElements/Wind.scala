package SimElements

import Utils.Vector2

case class Wind(val direction: Vector2[Double], val force: Double = 0.1) {
  // direction - direction and power of wind, force - how much does wind impact water

  def apply(particle: WaterParticle): WaterParticle = {
    WaterParticle(particle.position, -particle.force*force+direction*force, particle.height, particle.length)
  }

  def +(other: Wind): Wind = {
    Wind(direction+other.direction, (force+other.force)/2)
  }
}
