package featureflip.repository

import scala.concurrent.{ExecutionContext, Future}

import anorm._
import play.api.db.Database

import featureflip.models.FeatureFlip

class FeatureFlipRepository(database: Database)(implicit ec: ExecutionContext) {

  def list = Future {
    database.withConnection { implicit c =>
      SQL"""
        SELECT * from feature_flip
      """.as(FeatureFlip.parser.*)
    }
  }
}
