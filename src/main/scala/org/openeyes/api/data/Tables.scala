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

class SiteLasers(tag: Tag) extends Table[SiteLaser](tag, "site_laser") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def siteId = column[Int]("site_id")
  def laserId = column[Int]("laser_id")
  def * = (id.?, siteId, laserId) <> (SiteLaser.tupled, SiteLaser.unapply)
  def siteFK = foreignKey("site_fk", siteId, TableQuery[Sites])(site => site.id)
  def laserFK = foreignKey("laser_fk", laserId, TableQuery[Lasers])(laser => laser.id)
}

object Tables {
  lazy val lasers:TableQuery[Lasers] = TableQuery[Lasers]
  lazy val sites:TableQuery[Sites] = TableQuery[Sites]
  lazy val siteLasers:TableQuery[SiteLasers] = TableQuery[SiteLasers]
}
