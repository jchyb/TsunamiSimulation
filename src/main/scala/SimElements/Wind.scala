package SimElements

import Utils.Vector2

case class Wind(private val direction: Vector2[Double], private val impact: Double = 0.1) {
  // direction - direction and power of wind, impact - how much does wind impact water

  // applying wind and "friction"
  def apply(particle: WaterParticle): WaterParticle = {
    WaterParticle(particle.position, -particle.force*impact+direction*impact, particle.height, particle.length)
  }

  def +(other: Wind): Wind = {
    Wind(direction+other.direction, (impact+other.impact)/2)
  }
}
