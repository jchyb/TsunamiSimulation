package SimElements

import Utils.Vector2

class World(private val simTime : Int, receiver : Receiver){
  var water : Water = _
  var wind : Wind = _ //TODO: changing wind

  def setWave(wavePosition : Vector2[Int], waveStrength : Double): Unit = {
    water = new Water()
    water.initiateWave(wavePosition, waveStrength)
  }

  def setWind(windDirection: Vector2[Double], windForce: Double): Unit = {
    wind = Wind(windDirection, windForce)
  }

  def run(): Unit = {
    val deltaTime = 1
    for(i <- 0 to simTime){
      water.update(deltaTime, wind)
      receiver.receive(water.toIterable)
      Thread.sleep(100)
    }
  }
}
