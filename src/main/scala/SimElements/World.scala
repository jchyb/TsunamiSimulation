package SimElements

import Utils.Vector2

import scala.collection.mutable

class World(private val simTime : Int, receiver : Receiver){
  var water : Water = _
  var wind : Wind = _ //TODO: changing wind
  var breakwatersMap: mutable.HashMap[Vector2[Int], Breakwater] = _

  def initBreakwaters(list: List[(Vector2[Int], Double, Double)] = List((Vector2[Int](-50, 0), 15, 10))): Unit = {
    breakwatersMap = mutable.HashMap()
    list.foreach(e => addBreakwater(e._1, e._2, e._3))
  }

  // only before setWave
  def addBreakwater(position: Vector2[Int], r: Double, height: Double): Unit = {
    val breakwater = Breakwater(position, r, height)
    var list: List[Vector2[Int]] = for(i <- ((-r).toInt to r.toInt).toList; j <- ((-r).toInt to r.toInt).toList) yield Vector2[Int](i, j)
    list = list.filter(_.length()<=r)
    breakwatersMap.addAll(list.map(_+position).zip(List.fill(list.length)(breakwater)))
  }

  def setWave(wavePosition : Vector2[Int], waveStrength : Double): Unit = {
    water = new Water(breakwatersMap)
    water.initiateWave(wavePosition, waveStrength)
  }

  def setWind(windDirection: Vector2[Double], windForce: Double): Unit = {
    wind = Wind(windDirection, windForce)
  }

  def run(): Unit = {
    val deltaTime = 1
    for(i <- 0 to simTime){
      water.update(deltaTime, wind)
      receiver.receive(toIterable)
      Thread.sleep(100)
    }
  }
  def toIterable: Iterable[WorldEntity] = {
    water.toIterable ++ breakwatersMap.values
  }
}
