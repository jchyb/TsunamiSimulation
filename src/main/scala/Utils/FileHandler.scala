package Utils

import SimElements.{World, WorldEntity}
import io.circe.syntax._

object FileHandler {
  implicit val weDecoder: Decoder[WorldEntity] = deriveDecoder[WorldEntity]
  implicit val weEncoder: Encoder[WorldEntity] = deriveEncoder[WorldEntity]

  def fromFile : World = {

    new World(1,null)
  }

  def toFile(world : World) = {
    world.toIterable.toList.asJson.nospaces
  }

}
