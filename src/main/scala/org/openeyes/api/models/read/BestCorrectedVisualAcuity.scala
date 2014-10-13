package org.openeyes.api.models.read

import com.mongodb.casbah.MongoConnection
import com.novus.salat.dao.{ModelCompanion, SalatDAO}
import com.novus.salat.global._
import org.bson.types.ObjectId
import org.springframework.stereotype.Repository

case class BestCorrectedVisualAcuity(patientId: String, date: Long, side: String, value: Int)

@Repository
class BestCorrectedVisualAcuityDao extends ModelCompanion[BestCorrectedVisualAcuity, ObjectId] {
  val collection = MongoConnection()("openeyes")("bcva")
  val dao = new SalatDAO[BestCorrectedVisualAcuity, ObjectId](collection = collection) {}
}
