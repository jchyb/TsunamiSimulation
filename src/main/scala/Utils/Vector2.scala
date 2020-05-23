package Utils

case class Vector2[T](val x: T, val y: T)(implicit num: Numeric[T]) {

  import num._

  def +(other: Vector2[T]): Vector2[T] = new Vector2[T](num.plus(this.x, other.x), num.plus(this.y, other.y))

  def -(other: Vector2[T]): Vector2[T] = new Vector2[T](num.minus(this.x, other.x), num.minus(this.y, other.y))

  def unary_-() = new Vector2[T](num.negate(this.x), num.negate(this.x))

  def *(scalar: Double): Vector2[Double] =
    new Vector2[Double](this.x.toDouble * scalar, this.y.toDouble * scalar)

  def /(scalar: Double): Vector2[Double] =
    new Vector2[Double](this.x.toDouble / scalar, this.y.toDouble / scalar)

  def length(): Double = Math.sqrt((x * x + y * y).toDouble)

  def sumOfElements() : T = x + y

  def normalise(): Vector2[Double] =
    Vector2(this.x.toDouble / length(), this.y.toDouble / length())
}
