package controllers

import jp.t2v.lab.play2.auth.LoginLogout
import models.Account
import play.api._
import play.api.data.Form
import play.api.mvc._
import views.html

import play.api.data.Form
import play.api.data.Forms._

import scala.concurrent.Future
import play.api.libs.concurrent.Execution.Implicits.defaultContext

class Application extends Controller with LoginLogout with AuthConfigImpl {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  /** ログインFormはアプリケーションに応じて自由に作成してください。 */
  val loginForm = Form {
    mapping("name" -> text, "password" -> text)(Account.authenticate)(_.map(u => (u.name, "")))
      .verifying("Invalid name or password", result => result.isDefined)
  }

  /** ログインページはアプリケーションに応じて自由に作成してください。 */
  def login = Action { implicit request =>
    Ok(html.login(loginForm))
  }

  /**
    * ログアウト処理では任意の処理を行った後、
    * gotoLogoutSucceeded メソッドを呼び出した結果を返して下さい。
    *
    * gotoLogoutSucceeded メソッドは Future[Result] を返します。
    * 以下のようにflashingなどを追加することもできます。
    *
    *   gotoLogoutSucceeded.map(_.flashing(
    *     "success" -> "You've been logged out"
    *   ))
    */
  def logout = Action.async { implicit request =>
    // do something...
    gotoLogoutSucceeded
  }

  /**
    * ログイン処理では認証が成功した場合、
    * gotoLoginSucceeded メソッドを呼び出した結果を返して下さい。
    *
    * gotoLoginSucceeded メソッドも gotoLogoutSucceeded と同じく Future[Result] を返します。
    * 任意の処理を追加することも可能です。
    */
  def authenticate = Action.async { implicit request =>
    loginForm.bindFromRequest.fold(
      formWithErrors => Future.successful(BadRequest(html.login(formWithErrors))),
      user => gotoLoginSucceeded(user.get.id)
    )
  }

}
