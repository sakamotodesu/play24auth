import models.Account
import models.Role.{Administrator, NormalUser}
import play.api._

object Global extends GlobalSettings {

  override def onStart(app: Application) {
    if (Account.findAll().isEmpty) {
      Seq(
        Account(1, "secret", "Alice", Administrator),
        Account(2, "secret", "Bob", NormalUser),
        Account(3, "secret", "Chris", NormalUser)
      ) foreach Account.create
    }
  }
}
