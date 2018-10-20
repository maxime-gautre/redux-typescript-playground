package featureflip.controllers

import featureflip.repository.FeatureFlipRepository
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.ExecutionContext

class FeatureFlipController(
    cc: ControllerComponents,
    featureFlipRepository: FeatureFlipRepository
)(implicit ec: ExecutionContext)
    extends AbstractController(cc) {

  def list = Action.async { _ =>
    featureFlipRepository.list.map { featuresFlip =>
      Ok(Json.toJson(featuresFlip))
    }
  }
}
