package org.openeyes.api.commands

import org.bson.types.ObjectId
import org.openeyes.api.di.ProductionEnvironment
import org.openeyes.api.models.workflow.{Ticket,TicketDao}
import org.openeyes.api.utils.Date._

object generateSampleTickets {
  private val env = ProductionEnvironment

  private object ticketDao extends TicketDao

  def apply() = {
    val patients = env.patientService.findAll.iterator

    env.workflowService.findAll.foreach (wf =>
      for (i <- wf.steps.indices) {
        for (_ <- 0 until 10) {
          ticketDao.save(Ticket(new ObjectId, wf._id, patients.next, i, false, setTimestamp))
        }
      }
    )
  }
}
