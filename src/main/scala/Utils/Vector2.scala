package Utils

case class Vector2[T](x: T, y: T)(implicit num: Numeric[T]) {

  import num._

  def +(other: Vector2[T]): Vector2[T] = new Vector2[T](num.plus(this.x, other.x), num.plus(this.y, other.y))

  def -(other: Vector2[T]): Vector2[T] = new Vector2[T](num.minus(this.x, other.x), num.minus(this.y, other.y))

  def unary_-() = new Vector2[T](num.negate(this.x), num.negate(this.x))

  def *(scalar: Double): Vector2[Double] =
    new Vector2[Double](this.x.toDouble * scalar, this.y.toDouble * scalar)

  def /(scalar: Double): Vector2[Double] =
    new Vector2[Double](this.x.toDouble / scalar, this.y.toDouble / scalar)

  def >(other: Vector2[T]): Boolean = this.length()>other.length()

  def length(): Double = Math.sqrt((x * x + y * y).toDouble)

  def sumOfElements() : T = x + y

  def normalise(): Vector2[Double] =
    Vector2(this.x.toDouble / length(), this.y.toDouble / length())

  override def toString: String = "("+this.x.toString+","+this.y.toString+")"

  /*override def equals(other: Any): Boolean = { //Chyba się przyda do mapy?
    if(!other.isInstanceOf[Vector2[T]]) false
    else {
      val otherAsInstance = other.asInstanceOf[Vector2[T]]
      return this.x == otherAsInstance.x && this.y == otherAsInstance.y
    }
  }

  override def hashCode: Int = (31 * this.x.toDouble + 17 * this.y.toDouble).toInt*/
}
