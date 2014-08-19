package org.openeyes.api.models

import com.mongodb.casbah.MongoConnection
import com.novus.salat.annotations.raw.Key
import com.novus.salat.dao.{ModelCompanion, SalatDAO}
import com.novus.salat.global._
import org.bson.types.ObjectId


/**
 * Created by dave on 19/08/14.
 */
case class Observation(@Key("_id")
                       _id: ObjectId,
                       weight: Weight)

object Observation extends ModelCompanion[Observation, ObjectId] {

  val collection = MongoConnection()("openeyes")("observations")
  val dao = new SalatDAO[Observation, ObjectId](collection = collection) {}

}
