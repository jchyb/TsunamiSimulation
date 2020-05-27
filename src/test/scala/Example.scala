import SimElements.{Breakwater, Shore, WaterParticle, Wind}
import Utils.Vector2
import org.scalatest.funsuite.AnyFunSuite

class SetSuite extends AnyFunSuite {

  test("Normalisation Vector2") {
    assert(Vector2[Int](0,4).normalise() == Vector2(0,1))
    assert(Vector2[Double](0,4).normalise() == Vector2(0,1))
    assert(Vector2[Int](0,4).normalise() != Vector2(0,2))
  }
  test("Arithmetic Vector2") {
    assert(Vector2[Double](1, 5) + Vector2[Double](-3, -4) == Vector2[Double](-2, 1))
    assert(Vector2[Int](1, 5) - Vector2[Int](-3, -4) == Vector2[Int](4, 9))
    assert(Vector2[Double](1, -3) * 3 == Vector2[Double](3, -9))
    assert(Vector2[Double](1, -3) / 3 == Vector2[Double](1.0/3, -3.0/3))
    assert(-Vector2[Double](1, -3) == Vector2[Double](-1, 3))
    assert(-Vector2[Int](1, -3) == Vector2[Int](-1, 3))
  }
  test("Tools Vector2"){
    assert(Vector2[Int](10, 3) > Vector2[Int](1, 3))
    assert(Vector2[Double](1, 7) > Vector2[Double](2, 3))
    assert(Vector2[Double](1, 7).length() == Math.sqrt(50))
    assert(Vector2[Double](1, 7).length() == Vector2[Int](1,7).length())
    assert(Vector2[Int](10, 3).sumOfElements() == 13)
    assert(Vector2[Double](10.0, 3.0).sumOfElements() == 13.0)
    assert(Vector2[Int](1, 7) == Vector2[Int](1,7))
    assert(Vector2[Int](1, 7).double() == Vector2[Double](1,7))
    assert(Vector2[Int](1, 7) == Vector2[Int](1,7).int())
    assert(Vector2[Double](10.0, 3.0).toString == "(10.0, 3.0)")
  }
  val pos: Vector2[Int] = Vector2[Int](1,5)
  val force: Vector2[Double] = Vector2[Double](10,3)
  val height: Double = 10
  test("Test WaterParticle"){
    assert(WaterParticle(pos, force, height).update().length==10)
  }
  val wind: Wind = Wind(-force)
  test("Test Wind"){
    assert(wind(WaterParticle(pos, force, height)).force != force)
  }
  val breakwater: Breakwater = Breakwater(pos+Vector2[Int](1,1), 3, 7)
  test("Test Breakwater"){
    assert(breakwater.collide(WaterParticle(pos, force, height)).length==2)
    assert(breakwater.collide(WaterParticle(pos, force, height/2)).length==1)
    assert(breakwater.collide(WaterParticle(pos, force, height/2)).head.position!=pos)
  }
  val shore: Shore = new Shore(e => if (e.position.x>0) e else
    WaterParticle(e.position, e.force + Vector2(1, 0)*(0-e.position.x), e.height))
  test("Test Shore"){
    assert(shore(WaterParticle(pos, force, height)).force == force)
    assert(shore(WaterParticle(-pos, force, height)).force != force)
  }
  test("Invoking head on an empty Set should produce NoSuchElementException") {
    assertThrows[NoSuchElementException] {
      Set.empty.head
    }
  }
}