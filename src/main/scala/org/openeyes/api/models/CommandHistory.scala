package org.openeyes.api.models

import com.mongodb.casbah.MongoConnection
import com.novus.salat.dao.{ModelCompanion, SalatDAO}
import com.novus.salat.global._
import org.bson.types.ObjectId
import org.openeyes.api.cqrs.Command
import org.springframework.stereotype.Repository

@Repository
class CommandHistoryDao extends ModelCompanion[Command, ObjectId] {
  val collection = MongoConnection()("openeyes")("encounters")
  val dao = new SalatDAO[Command, ObjectId](collection = collection) {}
}
