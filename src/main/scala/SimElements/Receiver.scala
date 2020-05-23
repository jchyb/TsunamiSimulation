package SimElements

trait Receiver {
  def receive(list : Iterable[WorldEntity])
}
