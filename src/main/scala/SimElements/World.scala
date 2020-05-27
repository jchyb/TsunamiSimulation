package SimElements

import Utils.Vector2

import scala.collection.mutable

class World(private val simTime : Int, receiver : Receiver){
  var water : Water = _
  var wind : Wind = _ //TODO: changing wind
  var breakwatersMap: mutable.HashMap[Vector2[Int], Breakwater] = _

  def initBreakwaters(list: List[(Vector2[Int], Double, Double)] = List((Vector2[Int](10, 10), 3, 10))): Unit = {
    breakwatersMap = mutable.HashMap()
    list.foreach(e => addBreakwater(e._1, e._2, e._3))
  }

  // only before setWave
  def addBreakwater(position: Vector2[Int], r: Double, height: Double): Unit = {
    val breakwater = Breakwater(position, r, height)
//    var list = List()
//    for(i <- (-r).toInt to r.toInt; j <- (-r).toInt to r.toInt){
//      if()
//    }
    var list: List[Vector2[Int]] = for(i <- ((-r).toInt to r.toInt).toList; j <- ((-r).toInt to r.toInt).toList) yield Vector2[Int](i, j)
//    var list: List[Vector2[Int]] = ((-r).toInt to r.toInt).toList.flatMap(x => ((-r).toInt to r.toInt).toList.flatMap(y => (x, y)))
//    list.filter((position+_).length()<=r)
//    list.filter((e:Vector2[Int]) => (position+e).length()<=r)
    list = list.filter(e => (position+e).length()<=r)
    breakwatersMap.addAll(list.zip(List.fill(list.length)(breakwater)))
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
      receiver.receive(water.toIterable)
      Thread.sleep(100)
    }
  }
}
