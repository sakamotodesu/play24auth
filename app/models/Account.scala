package models

import anorm.SqlParser._
import anorm._
import models.Role.{Administrator, NormalUser}
import play.api.Play.current
import play.api.db.DB

/**
  * Created by sakamotominoru on 2016/02/14.
  */
case class Account(id: Int, password: String, name: String, role: Role)

object Account {

  implicit def rowToRole: Column[Role] = Column.nonNull1 { (value, meta) =>
    val MetaDataItem(qualified, nullable, clazz) = meta
    value match {
      case "Administrator" => Right(Administrator)
      case "NormalUser" => Right(NormalUser)
      case _ => Left(TypeDoesNotMatch("Cannot convert " + value + ":" + value.asInstanceOf[AnyRef].getClass + " to Role for column " + qualified))
    }
  }

  implicit def roleToStatement = new ToStatement[Role] {
    def set(s: java.sql.PreparedStatement, index: Int, aValue: Role): Unit = s.setObject(index, aValue.toString)
  }

  val account = {
    get[Int]("id") ~
            get[String]("password") ~
            get[String]("name") ~
            get[Role]("role") map {
      case id ~ password ~ name ~ role => Account(id, password, name, role)
    }
  }

  def authenticate(name: String, password: String): Option[Account] = DB.withConnection { implicit c =>
    SQL("select * from account where name = {name}").on('name -> name).as(account *).find(_.password == password)
  }

  def findByIdAsync(id: Int) = Some(Account(1, "password", "name", Administrator))

  def findByName(name: String) = Some(Account(1, "password", "name", Administrator))

  def findAll() = DB.withConnection { implicit c =>
    SQL("select * from account").as(account *)
  }

  def create(account: Account) = {
    DB.withConnection { implicit c =>
      SQL("insert into account (id, password, name, role) values ({id}, {password}, {name}, {role})")
              .on('id -> account.id, 'password -> account.password, 'name -> account.name, 'role -> account.role)
              .executeUpdate()
    }
  }


}