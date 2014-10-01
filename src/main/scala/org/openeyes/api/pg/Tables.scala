package org.openeyes.api.pg

import org.joda.time.DateTime
import org.openeyes.api.models._
import scala.slick.driver.PostgresDriver.simple._

class Patients(tag: Tag) extends Table[(Int,Patient)](tag, "patients") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def patient = column[Patient]("patient")(Types.json[Patient])
  def * = (id, patient)
}

class Encounters(tag: Tag) extends Table[(Int,Int,DateTime)](tag, "encounters") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def patientId = column[Int]("patientId")
  def patientFk = foreignKey("patientFk", patientId, TableQuery[Patients])(_.id)
  def createdAt = column[DateTime]("createdAt")(Types.dateTime)
  def * = (id, patientId, createdAt)
}

class Elements(tag: Tag) extends Table[(Int,Int,Element)](tag, "patients") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def encounterId = column[Int]("encounterId")
  def encounterFk = foreignKey("encounterFk", encounterId, TableQuery[Encounters])(_.id)
  def element = column[Element]("element")(Types.json[Element])
  def * = (id, encounterId, element)
}

object generateDdl {
  def apply() = (TableQuery[Patients].ddl ++ TableQuery[Encounters].ddl ++ TableQuery[Elements].ddl).createStatements.foreach(println)
}
