package org.openeyes.api.data

import org.openeyes.api.models.Site

import scala.slick.driver.MySQLDriver.simple._

object Sites extends Table[Site]("site") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc) // This is the primary key column
  def codeValue = column[String]("short_name")
  def label = column[String]("name")
  def systemId = column[String]("remote_id")

  // Every table needs a * projection with the same type as the table's type parameter
  def * = id.? ~ codeValue ~ label ~ systemId <> (Site, Site.unapply _)

  val findById = for {
    id <- Parameters[Int]
    c <- this if c.id is id
  } yield c

}
