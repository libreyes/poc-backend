package org.openeyes.api.data

import org.openeyes.api.models._

import scala.slick.driver.MySQLDriver.simple._

class Lasers(tag: Tag) extends Table[Laser](tag, "laser") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def codeValue = column[String]("code_value")
  def label = column[String]("label")
  def systemId = column[String]("system_id")
  def * = (id.?, codeValue, label, systemId) <> (Laser.tupled, Laser.unapply)
}

class Sites(tag: Tag) extends Table[Site](tag, "site") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def codeValue = column[String]("short_name")
  def label = column[String]("name")
  def systemId = column[String]("remote_id")
  def * = (id.?, codeValue, label, systemId) <> (Site.tupled, Site.unapply)
}
