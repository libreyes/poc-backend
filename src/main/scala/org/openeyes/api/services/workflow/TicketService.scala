package org.openeyes.api.services.workflow

import org.bson.types.ObjectId
import org.openeyes.api.Utilities.Date._
import org.openeyes.api.forms.workflow.TicketForm
import org.openeyes.api.models.Patient
import org.openeyes.api.models.workflow.Ticket

/**
 * Created by stu on 23/09/2014.
 */
object TicketService {

  def create(form: TicketForm) = {
    val patient = Patient.findOneById(new ObjectId(form.patientId))
    val ticket = Ticket(new ObjectId, new ObjectId(form.workflowId), patient.get, 0, false, setTimestamp)
    Ticket.save(ticket)
    ticket
  }

  def findAllForWorkflow(workflowId: String): Seq[Ticket] = {
    Ticket.findAllForWorkflow(workflowId)
  }
}
