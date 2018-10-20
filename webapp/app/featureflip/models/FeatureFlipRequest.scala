package featureflip.models

import play.api.libs.json.{Json, Reads}

case class FeatureFlipRequest(
    name: String,
    activated: Boolean
)

object FeatureFlipRequest {
  implicit val jsonReads: Reads[FeatureFlipRequest] =
    Json.reads[FeatureFlipRequest]
}
