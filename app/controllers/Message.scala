package controllers


import javax.inject.Inject

import jp.t2v.lab.play2.auth.AuthElement
import models.Role.{Administrator, NormalUser}
import play.api.mvc.Controller
import views.html

class Message @Inject() extends Controller with AuthElement with AuthConfigImpl {

  // StackAction の 引数に権限チェック用の (AuthorityKey, Authority) 型のオブジェクトを指定します。
  // 第二引数に RequestWithAttribute[AnyContent] => Result な関数を渡します。

  // AuthElement は loggedIn[A](implicit RequestWithAttribute[A]): User というメソッドをもっています。
  // このメソッドから認証/認可済みのユーザを取得することができます。

  def main = StackAction(AuthorityKey -> NormalUser) { implicit request =>
    val user = loggedIn
    val title = "message main"
    Ok(html.message.main(title))
  }

  def list = StackAction(AuthorityKey -> NormalUser) { implicit request =>
    val user = loggedIn
    val title = "all messages"
    Ok(html.message.list(title))
  }

  def detail(id: Int) = StackAction(AuthorityKey -> NormalUser) { implicit request =>
    val user = loggedIn
    val title = "messages detail "
    Ok(html.message.detail(title + id))
  }

  // このActionだけ、Administrator でなければ実行できなくなります。
  def write = StackAction(AuthorityKey -> Administrator) { implicit request =>
    val user = loggedIn
    val title = "write message"
    Ok(html.message.write(title))
  }

}