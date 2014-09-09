package org.openeyes.api.data

import scala.slick.driver.MySQLDriver.simple._


/**
 * Created by stu on 09/09/2014.
 */
// Definition of the COFFEES table
object Coffees extends Table[(String, Int, Double, Int, Int)]("COFFEES") {
  def name = column[String]("COF_NAME", O.PrimaryKey)
  def supID = column[Int]("SUP_ID")
  def price = column[Double]("PRICE")
  def sales = column[Int]("SALES")
  def total = column[Int]("TOTAL")
  def * = name ~ supID ~ price ~ sales ~ total

  // A reified foreign key relation that can be navigated to create a join
  def supplier = foreignKey("SUP_FK", supID, Suppliers)(_.id)
}
