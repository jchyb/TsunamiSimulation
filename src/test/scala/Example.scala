import Utils.Vector2
import org.scalatest.funsuite.AnyFunSuite

class SetSuite extends AnyFunSuite {

  test("An empty Set should have size 0") {
    assert(Vector2[Int](0,4).normalise() == Vector2(0,1))
  }

  test("An empty Set should have 0") {
    assert(Vector2[Int](0,4).normalise() == Vector2(0,2))
  }
  test("Invoking head on an empty Set should produce NoSuchElementException") {
    assertThrows[NoSuchElementException] {
      Set.empty.head
    }
  }
}