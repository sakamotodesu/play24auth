package models

import _root_.scalikejdbc.DBSession
import anorm.SqlParser._
import anorm._
import com.sun.xml.internal.bind.v2.TODO
import org.mindrot.jbcrypt.BCrypt
import play.api.db.DB

/**
  * Created by sakamotominoru on 2016/02/14.
  */
case class Account(id: Int, password: String, name: String, role: Role)

object Account {

  val account = {
    get[Int]("id") ~
      get[String]("password") ~
      get[String]("name") ~
      get[Role]("role") map {
      case id ~ password ~ name ~ role => Account(id,password,name,role )
    }
  }
  def authenticate(name: String, password: String): Option[Account] =DB.withConnection { implicit c =>
    SQL("select * from account where {name}").on('name -> name).as(account *).find(_.password==password)
  }

  def findByIdAsync(id: Int)= Some(Account(1,"password","name",Administrator))

  def findByName(name: String)= Some(Account(1,"password","name",Administrator))

  def findAll()= DB.withConnection { implicit c =>
    SQL("select * from account").as(account *)
  }

  def create(account: Account) = {}


}