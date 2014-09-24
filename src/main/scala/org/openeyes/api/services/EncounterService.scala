package org.openeyes.api.services

import org.bson.types.ObjectId
import org.openeyes.api.Utilities.Date._
import org.openeyes.api.forms.EncounterForm
import org.openeyes.api.models._
import org.openeyes.api.services.workflow.TicketService

/**
 * Created by stu on 02/09/2014.
 */
object EncounterService {

  def create(form: EncounterForm): Encounter = {
    val ticketId = form.ticketId match {
      case Some(ticketId:String) => Some(new ObjectId(ticketId))
      case _ => None
    }
    val encounter = Encounter(new ObjectId, new ObjectId(form.patientId), setTimestamp, form.elements,
      ticketId, form.stepIndex)
    Encounter.save(encounter)

    if(ticketId.isDefined && form.stepIndex.isDefined){
      TicketService.updateStepIndexOrComplete(ticketId.get, form.stepIndex.get)
    }

    encounter
  }

  def find(id: String) = {
    Encounter.findOneById(new ObjectId(id))
  }

  def findAll = {
    Encounter.findAll().toSeq
  }

  def findAllForPatient(patientId: String): Seq[Encounter] = {
    Encounter.findAllForPatient(patientId)
  }

}
