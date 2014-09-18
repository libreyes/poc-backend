package org.openeyes.api.models.workflow

import com.mongodb.casbah.MongoConnection
import com.mongodb.casbah.commons.MongoDBObject
import com.novus.salat.annotations.raw.Key
import com.novus.salat.dao.{ModelCompanion, SalatDAO}
import com.novus.salat.global._
import org.bson.types.ObjectId

case class PatientTicket(name: String, id: String)

case class Queue(@Key("_id") _id: ObjectId, tickets: List[PatientTicket])

object Queue extends ModelCompanion[Queue, ObjectId] {

  val collection = MongoConnection()("openeyes")("workflow-queues")
  val dao = new SalatDAO[Queue, ObjectId](collection = collection) {}

  def findAllForJobRole(roleId: String): Seq[Queue] = {
    find(
      MongoDBObject("roleId" -> new ObjectId(roleId))
    ).toSeq
  }
}

