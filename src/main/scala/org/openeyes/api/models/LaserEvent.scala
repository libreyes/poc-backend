package org.openeyes.api.models

import com.mongodb.casbah.MongoConnection
import com.novus.salat.annotations.raw.Key
import com.novus.salat.dao.{ModelCompanion, SalatDAO}
import com.novus.salat.global._
import org.bson.types.ObjectId


/**
 * Created by dave on 19/08/14.
 */
case class LaserEvent(@Key("_id")
                       _id: ObjectId,
                       leftEye: TreatedEye,
                       rightEye: TreatedEye,
                       laser: Laser,
                       site: Site)

case class AnteriorSegment(data: String)
case class Episode(events: Option[List[LaserEvent]])
case class Laser(name: String)
case class Patient(firstname: String, surname: String)
case class Procedure(label: String, codeValue: String, systemId: String)
case class Site(name: String)
case class TreatedEye(procedures: List[Procedure], anteriorSegment: AnteriorSegment)


object LaserEvent extends ModelCompanion[LaserEvent, ObjectId] {

  val collection = MongoConnection()("openeyes")("laser-events")
  val dao = new SalatDAO[LaserEvent, ObjectId](collection = collection) {}

}
