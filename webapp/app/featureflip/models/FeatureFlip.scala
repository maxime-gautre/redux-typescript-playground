package featureflip.models

import anorm.Macro
import play.api.libs.json.Json

case class FeatureFlip(
    id: Int,
    name: String,
    activated: Boolean
)

object FeatureFlip {
  implicit val parser = Macro.namedParser[FeatureFlip]

  implicit val jsonWriter = Json.writes[FeatureFlip]
}
