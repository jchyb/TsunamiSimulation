package SimElements

import Utils.Vector2

class World(private val simTimeInSeconds : Int){
  var water : Water = _

  def set(wavePosition : Vector2[Int], waveStrength : Double): Unit = {
    water = new Water(1)
    water.initiateWave(wavePosition, waveStrength)
  }
  def run(): Unit ={
    val deltaTime = 1
    for(i <- 0 to simTimeInSeconds){
      water.update(deltaTime)
    }

  }
}
