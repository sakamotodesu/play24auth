package models

import anorm.SqlParser._
import anorm._
import com.sun.xml.internal.bind.v2.TODO
import play.api.db.DB

/**
  * Created by sakamotominoru on 2016/02/14.
  */
case class Account(id: Int, password: String, name: String, role: Role)

object Account {

  val task = {
    get[Int]("id") ~
      get[String]("password") ~
      get[String]("name") ~
      get[Role]("role") map {
      case id ~ password ~ name ~ role => Account(id,password,name,role )
    }
  }

  def findByIdAsync(id: Int)= Some(Account(1,"password","name",Administrator))

  def findAll()= DB.withConnection { implicit c =>
    SQL("select * from task").as(task *)
  }

  def create(account: Account) = {}


}