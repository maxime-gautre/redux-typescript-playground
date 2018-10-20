package featureflip.controllers

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration._

import akka.util.Timeout
import org.scalatest.Matchers._
import org.scalatest.WordSpec
import org.scalatest.mockito.MockitoSugar
import org.mockito.Mockito.when
import play.api.http.Status._
import play.api.libs.json.Json
import play.api.test.Helpers.{contentAsJson, status}
import play.api.test.{FakeRequest, StubControllerComponentsFactory}

import featureflip.models.FeatureFlip
import featureflip.repository.FeatureFlipRepository

class FeatureFlipControllerTest
    extends WordSpec
    with StubControllerComponentsFactory
    with MockitoSugar {

  implicit val timeout: Timeout = 5.seconds

  "FeatureFlipController" should {
    "return the features flips as json" in {
      val featureFlipRepositoryMock = mock[FeatureFlipRepository]
      when(featureFlipRepositoryMock.list).thenReturn(
        Future.successful(
          List(
            FeatureFlip(1, "name", activated = true),
            FeatureFlip(2, "name2", activated = false),
          )))

      val controller = new FeatureFlipController(stubControllerComponents(),
                                                 featureFlipRepositoryMock)
      val result = controller.list().apply(FakeRequest())

      val json = Json.arr(
        Json.obj("id" -> 1, "name" -> "name", "activated" -> true),
        Json.obj("id" -> 2, "name" -> "name2", "activated" -> false)
      )
      status(result) shouldBe OK
      contentAsJson(result) shouldBe json
    }
  }
}
