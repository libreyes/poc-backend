package org.openeyes.api.pg

import org.joda.time.DateTime
import org.openeyes.api.models._
import org.openeyes.api.models.workflow._
import scala.slick.driver.PostgresDriver.simple._

class Patients(tag: Tag) extends Table[(Int,Patient)](tag, "patients") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def patient = column[Patient]("patient")(Types.json[Patient])
  def * = (id, patient)
}

class Workflows(tag: Tag) extends Table[(Int,String,String,Seq[Step])](tag, "workflows") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")
  def site = column[String]("site")
  def steps = column[Seq[Step]]("steps")(Types.json[Seq[Step]])
  def * = (id, name, site, steps)
}

class Tickets(tag: Tag) extends Table[(Int,Int,Int,Int,Boolean,DateTime)](tag, "tickets") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def patientId = column[Int]("patientId")
  def patientFk = foreignKey("patientFk", patientId, TableQuery[Patients])(_.id)
  def workflowId = column[Int]("workflowId")
  def workflowFk = foreignKey("workflowFk", workflowId, TableQuery[Workflows])(_.id)
  def stepIndex = column[Int]("stepIndex")
  def completed = column[Boolean]("completed")
  def createdAt = column[DateTime]("createdAt")(Types.dateTime)
  def * = (id, patientId, workflowId, stepIndex, completed, createdAt)
}

class Encounters(tag: Tag) extends Table[(Int,Int,DateTime,Option[Int],Option[Int])](tag, "encounters") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def patientId = column[Int]("patientId")
  def patientFk = foreignKey("patientFk", patientId, TableQuery[Patients])(_.id)
  def createdAt = column[DateTime]("createdAt")(Types.dateTime)
  def ticketId = column[Option[Int]]("ticketId")
  def ticketFk = foreignKey("ticketFk", ticketId, TableQuery[Tickets])(_.id)
  def stepIndex = column[Option[Int]]("stepIndex")
  def * = (id, patientId, createdAt, ticketId, stepIndex)
}

class Elements(tag: Tag) extends Table[(Int,Int,Element)](tag, "elements") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def encounterId = column[Int]("encounterId")
  def encounterFk = foreignKey("encounterFk", encounterId, TableQuery[Encounters])(_.id)
  def element = column[Element]("element")(Types.json[Element])
  def * = (id, encounterId, element)
}

object createTables {
  def apply() = {
    Database.forURL("jdbc:postgresql:openeyes", driver = "org.postgresql.Driver") withTransaction { implicit session =>
      (TableQuery[Patients].ddl ++
        TableQuery[Workflows].ddl ++
        TableQuery[Tickets].ddl ++
        TableQuery[Encounters].ddl ++
        TableQuery[Elements].ddl
      ).create
    }
  }
}
