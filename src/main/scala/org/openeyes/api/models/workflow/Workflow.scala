package org.openeyes.api.models.workflow

import com.mongodb.casbah.MongoConnection
import com.novus.salat.annotations.raw.Key
import com.novus.salat.dao.{SalatDAO, ModelCompanion}
import com.novus.salat.global._
import org.bson.types.ObjectId

case class Workflow(@Key("_id") _id: ObjectId, name: String, steps: List[WorkflowStep])

case class WorkflowStep(name: String, mandatoryFieldSets: List[String], optionalFieldSets: Option[List[String]])

object Workflow extends ModelCompanion[Workflow, ObjectId] {

  val collection = MongoConnection()("openeyes")("workflows")
  val dao = new SalatDAO[Workflow, ObjectId](collection = collection) {}
}
