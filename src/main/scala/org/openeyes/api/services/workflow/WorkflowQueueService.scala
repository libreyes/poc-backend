package org.openeyes.api.services.workflow

import org.bson.types.ObjectId
import org.openeyes.api.forms.workflow.WorkflowQueueForm
import org.openeyes.api.models.workflow.{PatientTicket, Queue}

/**
 * Created by dave on 17/09/2014.
 */
object WorkflowQueueService {

  def create(form: WorkflowQueueForm) = {
    val queue = Queue(new ObjectId, form.tickets)
    Queue.save(queue)
    queue
  }

  def find(id: String) = {
    Queue.findOneById(new ObjectId(id))
  }

  def findAll = {
    Queue.findAll().toSeq
  }

  def findAllForRole(roleId: String) = {
    Queue.findAllForJobRole(roleId)
  }

  /**
   * Move a PatientTicket from one WorkflowQueue to another.
   * @param ticket the PatientTicket to move
   * @param from the queue to move it from
   * @param to the queue to move it to
   */
  def move(ticket: PatientTicket, from: Queue, to: Queue) = {
    // We could do this a bunch of ways. Use the add/remove functions?
    // Of course, then we don't have any transactions, but we kind of don't anyway...
  }


  def add(ticket: PatientTicket, to: Queue) = {

  }

  def remove(ticket: PatientTicket, from: Queue) = {

  }

}
