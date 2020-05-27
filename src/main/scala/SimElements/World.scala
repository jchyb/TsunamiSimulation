package SimElements

import Utils.Vector2

import scala.collection.mutable

class World(private val steps : Int, receiver : Receiver){
  var water : Water = _
  var wind : Wind = _
  var shore : Shore = _
  var breakwatersMap: mutable.HashMap[Vector2[Int], Breakwater] = _
  var running = true

  def initBreakwaters(list: List[(Vector2[Int], Double, Double)] = List((Vector2[Int](-50, 0), 5, 10))): Unit = {
    breakwatersMap = mutable.HashMap()
    list.foreach(e => addBreakwater(e._1, e._2, e._3))
  }

  def initShore(func: WaterParticle => WaterParticle = identity): Unit = {
    shore = new Shore(func)
  }

  // only before setWave
  def addBreakwater(position: Vector2[Int], r: Double, height: Double): Unit = {
    val breakwater = Breakwater(position, r, height)
    var list: List[Vector2[Int]] = for(i <- ((-r).toInt to r.toInt).toList; j <- ((-r).toInt to r.toInt).toList) yield Vector2[Int](i, j)
    list = list.filter(_.length()<=r)
    breakwatersMap.addAll(list.map(_+position).zip(List.fill(list.length)(breakwater)))
  }

  def setWave(wavePosition : Vector2[Int], waveStrength : Double): Unit = {
    water = new Water(shore, breakwatersMap)
    water.initiateWave(wavePosition, waveStrength)
  }

  def setWind(windDirection: Vector2[Double], windForce: Double): Unit = {
    wind = Wind(windDirection, windForce)
  }

  def run(skip: Int = 1): Unit = {
    val deltaTime = 1
    for(i <- 0 to steps){
      water.update(deltaTime, wind)
      if(i%skip==0) {
        receiver.receive(toIterable)
        Thread.sleep(100)
      }
      if(!running) return
    }
  }
  def stop() : Unit ={ running = false }

  def toIterable: Iterable[WorldEntity] = {
    water.toIterable ++ breakwatersMap.values.toSet
  }
}
