package models


sealed trait Role

object Role {

  case object Administrator extends Role{
    override def toString = "Administrator"
  }

  case object NormalUser extends Role{
    override def toString = "NormalUser"
  }

  def valueOf(value: String): Role = value match {
    case "Administrator" => Administrator
    case "NormalUser" => NormalUser
    case _ => throw new IllegalArgumentException()
  }

}
