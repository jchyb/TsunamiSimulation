package SimElements

import Utils.Vector2

object World {
  var water : Water = _

  def set(wavePosition : Vector2[Int], waveStrength : Double): Unit = {
    water = new Water(1)
    water.initiateWave(wavePosition, waveStrength)
  }
}
