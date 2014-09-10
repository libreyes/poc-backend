package org.openeyes.api.data

import org.openeyes.api.models.Site

import scala.slick.driver.MySQLDriver.simple._

class Sites(tag: Tag) extends Table[Site](tag, "site") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def codeValue = column[String]("short_name")
  def label = column[String]("name")
  def systemId = column[String]("remote_id")
  def * = (id.?, codeValue, label, systemId) <>(Site.tupled, Site.unapply)
}
