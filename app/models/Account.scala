package models

import anorm.SqlParser._
import anorm._
import play.api.db.DB
import play.api.Play.current

/**
  * Created by sakamotominoru on 2016/02/14.
  */
case class Account(id: Int, password: String, name: String, role: Role)

object Account {

  implicit def rowToRole: Column[Role] = Column.nonNull { (value, meta) =>
    val MetaDataItem(qualified, nullable, clazz) = meta
    value match {
      case d: Role => Right(d)
      case _ => Left(TypeDoesNotMatch("Cannot convert " + value + ":" + value.asInstanceOf[AnyRef].getClass + " to Role for column " + qualified))
    }
  }
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