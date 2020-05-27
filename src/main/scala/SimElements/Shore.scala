package SimElements

class Shore(private val func: WaterParticle => WaterParticle) {
  // function describing the shore
  def apply(particle: WaterParticle): WaterParticle = {
    func(particle)
  }
}
