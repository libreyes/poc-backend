package org.openeyes.api.commands

import org.bson.types.ObjectId
import org.openeyes.api.models.workflow.Ticket
import org.openeyes.api.services.PatientService
import org.openeyes.api.services.workflow.WorkflowService
import org.openeyes.api.utils.Date._

object GenerateTickets {
  
  def forAllSteps() = {
    val patients = PatientService.findAll.iterator

    WorkflowService.findAll.foreach (wf =>
      for (i <- wf.steps.indices) {
        for (_ <- 0 until 10) {
          Ticket.save(Ticket(new ObjectId, wf._id, patients.next(), i, false, setTimestamp))
        }
      }
    )
  }

  def forStepZeroOnly() {
    val patients = PatientService.findAll.iterator.toArray
    val numberOfTickets = patients.length - 1

    WorkflowService.findAll.foreach (wf =>
      for (i <- 0 to numberOfTickets) {
        println("Creating ticket number: " + i)
        Ticket.save(Ticket(new ObjectId, wf._id, patients(i), 0, false, setTimestamp))
      }
    )
  }
}
