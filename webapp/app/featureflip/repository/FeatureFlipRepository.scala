package featureflip.repository

import scala.concurrent.{ExecutionContext, Future}

import anorm._
import play.api.db.Database

import featureflip.models.{FeatureFlip, FeatureFlipRequest}

class FeatureFlipRepository(database: Database)(implicit ec: ExecutionContext) {

  def list = Future {
    database.withConnection { implicit c =>
      SQL"""
        SELECT * from feature_flip
      """.as(FeatureFlip.parser.*)
    }
  }

  def create(featureFlipRequest: FeatureFlipRequest): Future[FeatureFlip] = {
    import featureFlipRequest._
    Future {
      database.withTransaction { implicit c =>
        SQL"""
          INSERT INTO feature_flip(name, activated)
          VALUES ($name, $activated)
          RETURNING *
        """.executeInsert(FeatureFlip.parser.single)
      }
    }
  }

  def update(flipId: Int, featureFlipRequest: FeatureFlipRequest): Future[Option[FeatureFlip]] = {
    import featureFlipRequest._
    Future {
      database.withTransaction { implicit c =>
        SQL"""
          UPDATE feature_flip
          SET name = $name, activated = $activated
          WHERE id = $flipId
          RETURNING *
        """.executeQuery.as(FeatureFlip.parser.singleOpt)
      }
    }
  }
}
