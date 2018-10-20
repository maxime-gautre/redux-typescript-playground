package featureflip.repository

import scala.concurrent.ExecutionContext.Implicits.global

import org.scalatest.Matchers._
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Seconds, Span}

import featureflip.helper.DatabaseIntegration
import featureflip.models.{FeatureFlip, FeatureFlipRequest}

class FeatureFlipRepositoryTest
    extends DatabaseIntegration
    with ScalaFutures
    with FeatureFlipFixtures {

  override implicit val patienceConfig = PatienceConfig(
    timeout = Span(1, Seconds)
  )

  override def beforeEachStmt: String = """"""

  "FeatureFlipRepository.list" should {
    "list feature flips" in { db =>
      val featureFlipRepository = new FeatureFlipRepository(db)
      val result = featureFlipRepository.list

      whenReady(result) { res =>
        res should contain theSameElementsAs featuresFlip
      }
    }
  }

  "FeatureFlipRepository.create" should {
    "insert a new feature flip" in { db =>
      val featureFlipRepository = new FeatureFlipRepository(db)
      val result = featureFlipRepository.create(
        FeatureFlipRequest("test", activated = false))

      whenReady(result) { res =>
        res shouldBe FeatureFlip(6, "test", activated = false)
      }
    }
  }

}

sealed trait FeatureFlipFixtures {
  val featuresFlip = List(
    FeatureFlip(1, "new-logo", activated = true),
    FeatureFlip(2, "show-settings", activated = true),
    FeatureFlip(3, "show-menu", activated = false),
    FeatureFlip(4, "new-policy", activated = false),
    FeatureFlip(5, "last-dynamic-update", activated = false)
  )
}
