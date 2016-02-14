package controllers

import jp.t2v.lab.play2.auth.LoginLogout
import play.api._
import play.api.mvc._

class Application extends Controller with LoginLogout with AuthConfigImpl {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

}
