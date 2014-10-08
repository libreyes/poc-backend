package org.openeyes.api.services.workflow

import org.bson.types.ObjectId
import org.openeyes.api.utils.Date._
import org.openeyes.api.forms.workflow.TicketForm
import org.openeyes.api.models.workflow.{Ticket,TicketDao}
import org.openeyes.api.services.PatientService

/**
 * Created by stu on 23/09/2014.
 */
class TicketService(ticketDao: TicketDao, patientService: PatientService, workflowService: WorkflowService) {
  def create(form: TicketForm) = {
    val patient = patientService.find(form.patientId)
    val ticket = Ticket(new ObjectId, new ObjectId(form.workflowId), patient.get, createdAt = setTimestamp)
    ticketDao.save(ticket)
    ticket
  }

  def find(id: String) = {
    ticketDao.findOneById(new ObjectId(id))
  }

  def findAllForWorkflow(workflowId: String, stepIndex: Option[Int], includeCompleted: Boolean = false): Seq[Ticket] = {
    ticketDao.findAllForWorkflow(workflowId, stepIndex, includeCompleted)
  }

  def updateStepIndexOrComplete(ticketId: ObjectId, currentStepIndex: Int) = {
    val ticket = ticketDao.findOneById(ticketId) match {
      case Some(ticket) => ticket
      case None => throw new RuntimeException("Ticket not found")
    }

    val workflowStepsCount = workflowService.find(ticket.workflowId.toString) match {
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

    ticketDao.update(ticket)
    // Return the ticket in case its needed
    ticket
  }
}
