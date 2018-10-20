import controllers.AssetsComponents
import core.controllers.HomeController
import play.api.db.evolutions.EvolutionsComponents
import play.api.db.{DBComponents, HikariCPComponents}
import play.api.routing.Router
import play.api.{
  ApplicationLoader,
  BuiltInComponentsFromContext,
  LoggerConfigurator
}
import play.filters.HttpFiltersComponents
import router.Routes

import featureflip.controllers.FeatureFlipController
import featureflip.repository.FeatureFlipRepository

class CustomApplicationLoader extends ApplicationLoader {
  def load(context: ApplicationLoader.Context) = {
    LoggerConfigurator(context.environment.classLoader).foreach {
      _.configure(context.environment)
    }
    new CustomComponents(context).application
  }
}

class CustomComponents(context: ApplicationLoader.Context)
    extends BuiltInComponentsFromContext(context)
    with HttpFiltersComponents
    with AssetsComponents
    with DBComponents
    with EvolutionsComponents
    with HikariCPComponents {

  // executionContexts
  val defaultEc = controllerComponents.executionContext

  applicationEvolutions

  lazy val router: Router = new Routes(
    httpErrorHandler,
    new HomeController(
      controllerComponents,
    ),
    new FeatureFlipController(
      controllerComponents,
      new FeatureFlipRepository(dbApi.database("featureflip"))
    ),
    new core.controllers.XAssets(
      environment,
      httpErrorHandler,
      assetsMetadata,
      controllerComponents
    )
  )
}
