package featureflip.controllers

import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.concurrent.duration._

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.util.Timeout
import org.scalatest.Matchers._
import org.scalatest.WordSpec
import org.scalatest.mockito.MockitoSugar
import org.mockito.Mockito.when
import play.api.http.Status._
import play.api.libs.json.Json
import play.api.test.Helpers.{contentAsJson, status}
import play.api.test.{FakeRequest, StubControllerComponentsFactory}

import featureflip.models.{FeatureFlip, FeatureFlipRequest}
import featureflip.repository.FeatureFlipRepository

class FeatureFlipControllerTest
  extends WordSpec
    with StubControllerComponentsFactory
    with MockitoSugar {

  implicit val actorSystem: ActorSystem = ActorSystem("FeatureFlipController")
  implicit val ec: ExecutionContextExecutor = actorSystem.dispatcher
  implicit val materializer: ActorMaterializer = ActorMaterializer.create(actorSystem)
  implicit val timeout: Timeout = 5.seconds

  "FeatureFlipController" should {
    "return the features flips as json" in {
      val featureFlipRepositoryMock = mock[FeatureFlipRepository]
      when(featureFlipRepositoryMock.list).thenReturn(Future.successful(
        List(
          FeatureFlip(1, "name", activated = true),
          FeatureFlip(2, "name2", activated = false),
        )
      ))

      val controller = new FeatureFlipController(stubControllerComponents(), featureFlipRepositoryMock)
      val result = controller.list().apply(FakeRequest())

      val json = Json.arr(
        Json.obj("id" -> 1, "name" -> "name", "activated" -> true),
        Json.obj("id" -> 2, "name" -> "name2", "activated" -> false)
      )
      status(result) shouldBe OK
      contentAsJson(result) shouldBe json
    }

    "create a feature flip" in {
      val featureFlipRepositoryMock = mock[FeatureFlipRepository]
      val featureFlipRequest = FeatureFlipRequest("name", activated = true)
      when(featureFlipRepositoryMock.create(featureFlipRequest)).thenReturn(
        Future.successful(FeatureFlip(1, "name", activated = true))
      )

      val controller = new FeatureFlipController(stubControllerComponents(), featureFlipRepositoryMock)
      val result = controller.create().apply(FakeRequest().withBody(featureFlipRequest))

      val json = Json.obj("id" -> 1, "name" -> "name", "activated" -> true)

      status(result) shouldBe CREATED
      contentAsJson(result) shouldBe json
    }

    "update a feature flip if exist" in {
      val featureFlipRepositoryMock = mock[FeatureFlipRepository]
      val featureFlipRequest = FeatureFlipRequest("name", activated = true)
      when(featureFlipRepositoryMock.update(1, featureFlipRequest)).thenReturn(
        Future.successful(Some(FeatureFlip(1, "name", activated = true)))
      )

      val controller = new FeatureFlipController(stubControllerComponents(), featureFlipRepositoryMock)
      val result = controller.update(1).apply(FakeRequest().withBody(featureFlipRequest))

      val json = Json.obj("id" -> 1, "name" -> "name", "activated" -> true)

      status(result) shouldBe OK
      contentAsJson(result) shouldBe json
    }

    "return not found if flip is not found and cannot be updated" in {
      val featureFlipRepositoryMock = mock[FeatureFlipRepository]
      val featureFlipRequest = FeatureFlipRequest("name", activated = true)
      when(featureFlipRepositoryMock.update(1, featureFlipRequest)).thenReturn(
        Future.successful(None)
      )

      val controller = new FeatureFlipController(stubControllerComponents(), featureFlipRepositoryMock)
      val result = controller.update(1).apply(FakeRequest().withBody(featureFlipRequest))

      status(result) shouldBe NOT_FOUND
    }
  }
}
