package org.openeyes.api.forms.workflow

import org.openeyes.api.models.workflow.Step

/**
 * Created by jamie on 15/09/2014.
 */
case class WorkflowForm(name: String, site: String, steps: List[Step])
