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
    val ticket = Ticket(new ObjectId, new ObjectId(form.workflowId), patient.get, createdAt = setTimestamp)
    Ticket.save(ticket)
    ticket
  }

  def find(id: String) = {
    Ticket.findOneById(new ObjectId(id))
  }

  def findAllForWorkflow(workflowId: String, stepIndex: Option[Int], includeCompleted: Boolean = false): Seq[Ticket] = {
    Ticket.findAllForWorkflow(workflowId, stepIndex, includeCompleted)
  }

  def updateStepIndexOrComplete(ticketId: ObjectId, currentStepIndex: Int) = {
    val ticket = Ticket.findOneById(ticketId) match {
      case Some(ticket) => ticket
      case None => throw new RuntimeException("Ticket not found")
    }

    val workflowStepsCount = WorkflowService.find(ticket.workflowId.toString) match {
      case Some(workflow) => (workflow.steps.length - 1)
      case None => throw new RuntimeException("Workflow not found")
    }

    if(currentStepIndex < workflowStepsCount){
      // Increment stepIndex to the next step
      ticket.stepIndex = currentStepIndex + 1
    }else{
      // Else set to completed as we have hit the steps limit
      ticket.completed = true
    }

    Ticket.update(ticket)
    // Return the ticket in case its needed
    ticket
  }
}
