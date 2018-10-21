package featureflip.controllers

import featureflip.repository.FeatureFlipRepository
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}
import scala.concurrent.ExecutionContext

import featureflip.models.FeatureFlipRequest

class FeatureFlipController(
  cc: ControllerComponents,
  featureFlipRepository: FeatureFlipRepository
)(implicit ec: ExecutionContext)
  extends AbstractController(cc) {

  def list = Action.async { _ =>
    println("called")
    featureFlipRepository.list.map { featuresFlip =>
      Ok(Json.toJson(featuresFlip))
    }
  }

  def create = Action.async(parse.json[FeatureFlipRequest]) { request =>
    featureFlipRepository.create(request.body).map { featureFlip =>
      Created(Json.toJson(featureFlip))
    }
  }

  def update(flipId: Int) = Action.async(parse.json[FeatureFlipRequest]) { request =>
    featureFlipRepository.update(flipId, request.body).map {
      case Some(featureFlip) => Ok(Json.toJson(featureFlip))
      case None => NotFound(Json.obj("id" -> flipId, "message" -> s"Flip $flipId not found"))
    }
  }
}
