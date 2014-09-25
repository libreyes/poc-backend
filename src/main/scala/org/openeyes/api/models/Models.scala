package org.openeyes.api.models

import com.mongodb.casbah.MongoConnection
import com.mongodb.casbah.commons.MongoDBObject
import com.novus.salat.annotations.raw.Key
import com.novus.salat.dao.{ModelCompanion, SalatDAO}
import com.novus.salat.global._
import org.bson.types.ObjectId

case class Episode(events: Option[List[LaserEvent]])

// NOTE: Added id to the Laser class so we can fake its persistence on the front end.
case class Laser(id: String, codeValue: String, label: String, systemId: String)

case class LaserEvent(@Key("_id") _id: ObjectId, patientId: String, leftEye: TreatedEye, rightEye: TreatedEye,
                      laser: Laser, site: Site, laserOperator: LaserOperator, createdAt: Long)

// NOTE: Added id to the LaserOperator class so we can fake its persistence on the front end.
case class LaserOperator(id: String, firstName: String, surname: String)

// NOTE: Added id to the Procedure class so we can fake its persistence on the front end.
case class Procedure(id: String, codeValue: String, label: String, systemId: String)

// NOTE: Added id to the Site class so we can fake its persistence on the front end.
case class Site(id: String, codeValue: String, label: String, systemId: String)

case class TreatedEye(procedures: List[Procedure], anteriorSegment: AnteriorSegment)

object LaserEvent extends ModelCompanion[LaserEvent, ObjectId] {

  val collection = MongoConnection()("openeyes")("laser-events")
  val dao = new SalatDAO[LaserEvent, ObjectId](collection = collection) {}

  def findAllForPatient(patientId: String): Seq[LaserEvent] = {
    find(
      MongoDBObject("patientId" -> patientId)
    ).toSeq
  }
}



