package org.openeyes.api.commands

import org.bson.types.ObjectId
import org.openeyes.api.models.workflow.Ticket
import org.openeyes.api.services.PatientService
import org.openeyes.api.services.workflow.WorkflowService
import org.openeyes.api.utils.Date._

object generateSampleTickets {
  def apply() = {
    val patients = PatientService.findAll.iterator

    WorkflowService.findAll.foreach (wf =>
      for (i <- wf.steps.indices) {
        for (_ <- 0 until 10) {
          Ticket.save(Ticket(new ObjectId, wf._id, patients.next, i, false, setTimestamp))
        }
      }
    )
  }
}
