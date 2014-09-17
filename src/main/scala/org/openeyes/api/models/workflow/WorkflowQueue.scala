package org.openeyes.api.models.workflow

import com.mongodb.casbah.MongoConnection
import com.mongodb.casbah.commons.MongoDBObject
import com.novus.salat.dao.{ModelCompanion, SalatDAO}
import com.novus.salat.global._
import org.bson.types.ObjectId

case class WorkflowQueue(tickets: List[PatientTicket])

case class PatientTicket(name: String, id: String)

object WorkflowQueue extends ModelCompanion[WorkflowQueue, ObjectId] {

  val collection = MongoConnection()("openeyes")("workflow-queues")
  val dao = new SalatDAO[WorkflowQueue, ObjectId](collection = collection) {}

  def findAllForJobRole(roleId: String): Seq[WorkflowQueue] = {
    find(
      MongoDBObject("roleId" -> new ObjectId(roleId))
    ).toSeq
  }
}