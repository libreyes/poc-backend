package org.openeyes.api.models.workflow

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
case class Ticket(@Key("_id") _id: ObjectId, workflowId: ObjectId, patient: Patient, stepIndex: Int, completed: Boolean,
                  createdAt: Long)

object Ticket extends ModelCompanion[Ticket, ObjectId] {

  val collection = MongoConnection()("openeyes")("tickets")
  val dao = new SalatDAO[Ticket, ObjectId](collection = collection) {}

  def findAllForWorkflow(workflowId: String) = {
    find(
      MongoDBObject("workflowId" -> new ObjectId(workflowId))
    ).toSeq
  }
}
