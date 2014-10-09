package org.openeyes.api.cqrs.handlers

import org.bson.types.ObjectId
import org.openeyes.api.cqrs._
import org.openeyes.api.models.EncounterDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class CreateEncounter @Autowired() (encounterDao: EncounterDao) extends CommandHandler[commands.CreateEncounter] {
  def handle(c: commands.CreateEncounter) {
    encounterDao.save(c.encounter)
  }
}
