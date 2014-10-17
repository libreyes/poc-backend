package org.openeyes.api.models

import com.mongodb.casbah.MongoConnection
import com.mongodb.casbah.commons.MongoDBObject
import com.novus.salat.annotations.raw.Key
import com.novus.salat.dao.{ModelCompanion, SalatDAO}
import com.novus.salat.global._
import com.typesafe.config.ConfigFactory
import org.bson.types.ObjectId
import org.openeyes.api.config.AppConfig

/**
 * Created by stu on 24/09/2014.
 */
case class Encounter(@Key("_id") _id: ObjectId, patientId: ObjectId, createdAt: Long, elements: List[Element],
                     ticketId: Option[ObjectId], stepIndex: Option[Int])

object Encounter extends ModelCompanion[Encounter, ObjectId] {

  val config = new AppConfig(ConfigFactory.load())
  val collection = MongoConnection()(config.database)("encounters")
  val dao = new SalatDAO[Encounter, ObjectId](collection = collection) {}

  def findAllForPatient(patientId: String): Seq[Encounter] = {
    find(
      MongoDBObject("patientId" -> new ObjectId(patientId))
    ).toSeq
  }
}
