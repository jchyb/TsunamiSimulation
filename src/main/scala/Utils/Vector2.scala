package Utils

class Vector2[T](val x: T, val y: T)(implicit num: Numeric[T]) {

  import num._

  def +(other: Vector2[T]): Vector2[T] = new Vector2[T](num.plus(this.x, other.x), num.plus(this.y, other.y))

  def -(other: Vector2[T]): Vector2[T] = new Vector2[T](num.minus(this.x, other.x), num.minus(this.y, other.y))

  def *(scalar: Double): Vector2[Double] =
    new Vector2[Double](this.x.asInstanceOf[Double] * scalar, this.y.asInstanceOf[Double] * scalar)

  def /(scalar: Double): Vector2[Double] =
    new Vector2[Double](this.x.asInstanceOf[Double] / scalar, this.y.asInstanceOf[Double] / scalar)

  def length(): Double = Math.sqrt((x * x + y * y).asInstanceOf[Double])

  def normalise(): Vector2[Double] =
    new Vector2(this.x.asInstanceOf[Double] / length(), this.y.asInstanceOf[Double] / length())
}
