package org.openeyes.api.di

import org.openeyes.api.models._
import org.openeyes.api.models.workflow._
import org.openeyes.api.services._
import org.openeyes.api.services.workflow._

trait Environment {
  val elementService: ElementService;
  val encounterService: EncounterService;
  val patientService: PatientService;
  val ticketService: TicketService;
  val workflowService: WorkflowService;
}

object ProductionEnvironment {
  lazy val elementService = new ElementService(encounterDao);
  lazy val encounterService = new EncounterService(encounterDao, ticketService);
  lazy val patientService = new PatientService(patientDao);
  lazy val ticketService = new TicketService(ticketDao, patientService, workflowService);
  lazy val workflowService = new WorkflowService(workflowDao);

  protected object encounterDao extends EncounterDao
  protected object patientDao extends PatientDao
  protected object ticketDao extends TicketDao
  protected object workflowDao extends WorkflowDao
}
