package org.openeyes.api.data

import scala.slick.driver.MySQLDriver.simple._
import Database.threadLocalSession

case class Supplier(id: Option[Int], name: String, street: String, city: String, state: String, zip: String)

/**
 * Created by stu on 09/09/2014.
 */
// Definition of the SUPPLIERS table
object Suppliers extends Table[Supplier]("SUPPLIERS") {
  def id = column[Int]("SUP_ID", O.PrimaryKey, O.AutoInc) // This is the primary key column
  def name = column[String]("SUP_NAME")
  def street = column[String]("STREET")
  def city = column[String]("CITY")
  def state = column[String]("STATE")
  def zip = column[String]("ZIP")

  // Every table needs a * projection with the same type as the table's type parameter
  def * = id.? ~ name ~ street ~ city ~ state ~ zip <> (Supplier, Supplier.unapply _)

  val findById = for {
    id <- Parameters[Int]
    c <- this if c.id is id
  } yield c

}

