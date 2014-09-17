package org.openeyes.api.models.workflow

/**
 * Created by dave on 17/09/2014.
 */
case class Queue(tickets: List[PatientTicket])
case class PatientTicket(patient: WorkflowPatientView)
case class WorkflowPatientView(name: String)