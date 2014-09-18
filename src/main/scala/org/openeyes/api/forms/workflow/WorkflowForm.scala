package org.openeyes.api.forms.workflow

import org.openeyes.api.models.workflow.WorkflowStep

/**
 * Created by jamie on 15/09/2014.
 */
case class WorkflowForm(name: String, steps: List[WorkflowStep])
