package org.openeyes.api.models

import com.mongodb.casbah.MongoConnection
import com.mongodb.casbah.commons.MongoDBObject
import com.novus.salat.annotations.raw.Key
import com.novus.salat.dao.{ModelCompanion, SalatDAO}
import com.novus.salat.global._
import org.bson.types.ObjectId
import org.springframework.stereotype.Repository

/**
 * Created by stu on 24/09/2014.
 */
case class Encounter(@Key("_id") _id: ObjectId, patientId: ObjectId, createdAt: Long, elements: List[Element],
                     ticketId: Option[ObjectId], stepIndex: Option[Int])

@Repository
class EncounterDao extends ModelCompanion[Encounter, ObjectId] {

  val collection = MongoConnection()("openeyes")("encounters")
  val dao = new SalatDAO[Encounter, ObjectId](collection = collection) {}

  def findAllForPatient(patientId: String): Seq[Encounter] = {
    find(
      MongoDBObject("patientId" -> new ObjectId(patientId))
    ).toSeq
  }
}
