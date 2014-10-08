package org.openeyes.api.commands

import org.bson.types.ObjectId
import org.openeyes.api.models.workflow.{Ticket,TicketDao}
import org.openeyes.api.utils.Date._
import org.openeyes.api.services.PatientService
import org.openeyes.api.services.workflow.WorkflowService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class generateSampleTickets @Autowired() (patientService: PatientService, workflowService: WorkflowService) {
  private object ticketDao extends TicketDao

  def apply() = {
    val patients = patientService.findAll.iterator

    workflowService.findAll.foreach (wf =>
      for (i <- wf.steps.indices) {
        for (_ <- 0 until 10) {
          ticketDao.save(Ticket(new ObjectId, wf._id, patients.next, i, false, setTimestamp))
        }
      }
    )
  }
}
