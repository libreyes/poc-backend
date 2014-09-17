package org.openeyes.api.forms

import org.openeyes.api.models.WorkflowStep

/**
 * Created by jamie on 15/09/2014.
 */
case class WorkflowForm(name: String, steps: List[WorkflowStep])
