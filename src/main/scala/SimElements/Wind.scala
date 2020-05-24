package SimElements

import Utils.Vector2

case class Wind(private val direction: Vector2[Double], private val impact: Double = 0.001) {
  // direction - direction and power of wind, impact - how much does wind impact water

  private val minHeightImpact = 0.001
  private val maxHeightImpact = 2
  private val absoluteHeightImpact = 5

  // applying wind and "friction"
  def apply(particle: WaterParticle): WaterParticle = {
    WaterParticle(particle.position,
      particle.force-particle.force*impact+direction*impact*Math.max(Math.min(particle.height*absoluteHeightImpact, maxHeightImpact), minHeightImpact),
      particle.height, particle.length)
  }

  def +(other: Wind): Wind = {
    Wind(direction+other.direction, (impact+other.impact)/2)
  }
}
