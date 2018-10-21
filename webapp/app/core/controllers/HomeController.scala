package core.controllers

import play.api.mvc._

class HomeController(
    cc: ControllerComponents,
) extends AbstractController(cc) {

  def index = Action {
    Ok(core.views.html.index())
  }

  def about = Action {
    Ok(sbt.BuildInfo.toString)
  }
}
