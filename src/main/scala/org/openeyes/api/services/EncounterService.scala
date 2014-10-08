package org.openeyes.api.services

import org.bson.types.ObjectId
import org.openeyes.api.utils.Date._
import org.openeyes.api.forms.EncounterForm
import org.openeyes.api.models._
import org.openeyes.api.services.workflow.TicketService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Created by stu on 02/09/2014.
 */
@Service
class EncounterService @Autowired() (encounterDao: EncounterDao, ticketService: TicketService) {
  def create(form: EncounterForm): Encounter = {
    val ticketId = form.ticketId match {
      case Some(ticketId: String) => Some(new ObjectId(ticketId))
      case _ => None
    }

    val encounter = Encounter(new ObjectId, new ObjectId(form.patientId), setTimestamp, form.elements,
      ticketId, form.stepIndex)
    encounterDao.save(encounter)

    if(ticketId.isDefined && form.stepIndex.isDefined){
      ticketService.updateStepIndexOrComplete(ticketId.get, form.stepIndex.get)
    }

    encounter
  }

  def find(id: String) = {
    encounterDao.findOneById(new ObjectId(id))
  }

  def findAll = {
    encounterDao.findAll().toSeq
  }

  def findAllForPatient(patientId: String): Seq[Encounter] = {
    encounterDao.findAllForPatient(patientId)
  }

}
