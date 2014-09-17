package org.openeyes.api.services

import org.bson.types.ObjectId
import org.openeyes.api.forms.WorkflowForm
import org.openeyes.api.models._

/**
 * Created by stu on 02/09/2014.
 */
object WorkflowService {

  def create(form: WorkflowForm): Workflow = {
    val workflow = Workflow(new ObjectId, form.name, form.steps)
    Workflow.save(workflow)
    workflow
  }

  def find(id: String) = {
    Workflow.findOneById(new ObjectId(id))
  }

  def findAll = {
    Workflow.findAll().toSeq
  }

}
