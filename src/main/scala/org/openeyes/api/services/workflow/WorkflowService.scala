package org.openeyes.api.services.workflow

import org.bson.types.ObjectId
import org.openeyes.api.models.workflow.Workflow
import org.openeyes.api.models.workflow.WorkflowDao
import org.openeyes.api.forms.workflow.WorkflowForm

/**
 * Created by stu on 02/09/2014.
 */
class WorkflowService(workflowDao: WorkflowDao) {
  def create(form: WorkflowForm): Workflow = {
    val workflow = Workflow(new ObjectId, form.name, form.site, form.steps)
    workflowDao.save(workflow)
    workflow
  }

  def find(id: String) = {
    workflowDao.findOneById(new ObjectId(id))
  }

  def findAll = {
    workflowDao.findAll().toSeq
  }
}
