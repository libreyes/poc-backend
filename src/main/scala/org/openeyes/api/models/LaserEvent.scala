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
                       leftEye: Option[Eye],
                       rightEye: Option[Eye])

case class Eye(treatments: Option[List[Treatment]])
case class Treatment(procedures: Option[List[Procedure]])
case class Procedure(name: String)

object LaserEvent extends ModelCompanion[LaserEvent, ObjectId] {

  val collection = MongoConnection()("openeyes")("laser-events")
  val dao = new SalatDAO[LaserEvent, ObjectId](collection = collection) {}

}
