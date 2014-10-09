package org.openeyes.api.cqrs.finders

import com.mongodb.casbah.Imports._
import org.openeyes.api.cqrs._
import org.openeyes.api.models
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class Encounter @Autowired() (dao: models.EncounterDao) extends Finder[models.Encounter] {
  def find(q: Query) = {
    dao.find(q.params.asDBObject).toSeq
  }
}
