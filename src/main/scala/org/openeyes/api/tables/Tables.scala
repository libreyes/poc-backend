package org.openeyes.api.tables

import scala.slick.driver.MySQLDriver.simple._

/**
 * Created by stu on 08/09/2014.
 */
object Tables {

  class Sites(tag: Tag) extends Table[(Int, String, String, String)](tag, "sites") {
    def id      = column[Int]("id", O.PrimaryKey) // This is the primary key column
    def codeValue  = column[String]("short_name")
    def label    = column[String]("name")
    def systemId    = column[String]("remote_id")

    // Every table needs a * projection with the same type as the table's type parameter
    def * = (id, codeValue, label, systemId)
  }

  val sites = TableQuery[Sites]
}
