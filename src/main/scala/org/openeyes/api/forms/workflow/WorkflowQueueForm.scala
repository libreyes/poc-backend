package org.openeyes.api.forms.workflow

import org.openeyes.api.models.workflow.PatientTicket

case class WorkflowQueueForm(tickets: List[PatientTicket])
