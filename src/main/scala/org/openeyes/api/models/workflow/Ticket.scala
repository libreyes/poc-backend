package org.openeyes.api.models.workflow

import com.mongodb.WriteConcern
import com.mongodb.casbah.MongoConnection
import com.mongodb.casbah.commons.MongoDBObject
import com.novus.salat.annotations.raw.Key
import com.novus.salat.dao.{ModelCompanion, SalatDAO}
import com.novus.salat.global._
import org.bson.types.ObjectId
import org.openeyes.api.models.Patient

/**
 * Created by stu on 23/09/2014.
 */
case class Ticket(@Key("_id") _id: ObjectId, workflowId: ObjectId, patient: Patient, var stepIndex: Int = 0,
                  var completed: Boolean = false, createdAt: Long)

trait TicketDao extends ModelCompanion[Ticket, ObjectId] {

  val collection = MongoConnection()("openeyes")("tickets")
  val dao = new SalatDAO[Ticket, ObjectId](collection = collection) {}

  def findAllForWorkflow(workflowId: String, stepIndex: Option[Int], includeCompleted: Boolean = false) = {
    val builder = MongoDBObject.newBuilder
    builder += "workflowId" -> new ObjectId(workflowId)
    if (stepIndex.isDefined) builder += "stepIndex" -> stepIndex.get
    if (!includeCompleted) builder += "completed" -> false
    find(builder.result).toSeq
  }

  def update(ticket: Ticket) = {
    dao.update(MongoDBObject("_id" -> ticket._id), ticket, false, false, new WriteConcern)
  }

}
