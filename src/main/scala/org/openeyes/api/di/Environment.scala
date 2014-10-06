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
  object elementService extends ElementService {
    lazy protected val encounterDao = ProductionEnvironment.encounterDao
  }

  object encounterService extends EncounterService {
    lazy protected val ticketService = ProductionEnvironment.ticketService
    lazy protected val encounterDao = ProductionEnvironment.encounterDao
  }

  object patientService extends PatientService {
    lazy protected val patientDao = ProductionEnvironment.patientDao
  }

  object ticketService extends TicketService {
    lazy protected val patientService = ProductionEnvironment.patientService
    lazy protected val workflowService = ProductionEnvironment.workflowService
    lazy protected val ticketDao = ProductionEnvironment.ticketDao
  }

  object workflowService extends WorkflowService {
    lazy protected val workflowDao = ProductionEnvironment.workflowDao
  }

  protected object encounterDao extends EncounterDao
  protected object patientDao extends PatientDao
  protected object ticketDao extends TicketDao
  protected object workflowDao extends WorkflowDao
}
