package org.openeyes.api.cqrs.handlers

import org.bson.types.ObjectId
import org.openeyes.api.cqrs._
import org.openeyes.api.models.{Encounter,EncounterDao}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ReadEncounter @Autowired() (dao: EncounterDao) extends ReadHandler[Encounter, ObjectId] {
  def handle(id: ObjectId) = dao.findOneById(id)
}
