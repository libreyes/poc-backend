package org.openeyes.api.services.workflow

import org.bson.types.ObjectId
import org.openeyes.api.forms.workflow.WorkflowQueueForm
import org.openeyes.api.models.workflow.{PatientTicket, WorkflowQueue}

/**
 * Created by dave on 17/09/2014.
 */
object WorkflowQueueService {

  def create(form: WorkflowQueueForm) = {
    val queue = WorkflowQueue(form.tickets)
    WorkflowQueue.save(queue)
    queue
  }

  def find(id: String) = {
    WorkflowQueue.findOneById(new ObjectId(id))
  }

  def findAll = {
    WorkflowQueue.findAll().toSeq
  }

  def findAllForRole(roleId: String) = {
    WorkflowQueue.findAllForJobRole(roleId)
  }

  /**
   * Move a PatientTicket from one WorkflowQueue to another.
   * @param ticket the PatientTicket to move
   * @param from the queue to move it from
   * @param to the queue to move it to
   */
  def move(ticket: PatientTicket, from: WorkflowQueue, to: WorkflowQueue) = {
  }

}
