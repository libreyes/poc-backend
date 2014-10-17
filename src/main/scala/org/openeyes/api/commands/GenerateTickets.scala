package org.openeyes.api.commands

import java.nio.charset.StandardCharsets
import java.nio.file.{Paths, Files}

import org.bson.types.ObjectId
import org.openeyes.api.models.workflow.Ticket
import org.openeyes.api.services.PatientService
import org.openeyes.api.services.workflow.WorkflowService
import org.openeyes.api.utils.Date._

import scala.io.Source

object GenerateTickets {
  
  def forAllSteps() = {
    val patients = PatientService.findAll.iterator

    WorkflowService.findAll.foreach (wf =>
      for (i <- wf.steps.indices) {
        for (_ <- 0 until 10) {
          Ticket.save(Ticket(new ObjectId, wf._id, patients.next(), i, false, setTimestamp))
        }
      }
    )
  }

  def forStepZeroOnly() {
    val patients = PatientService.findAll.iterator.toArray
    val numberOfTickets = patients.length - 1

    WorkflowService.findAll.foreach (wf =>
      for (i <- 0 to numberOfTickets) {
        println("Creating ticket number: " + i)
        Ticket.save(Ticket(new ObjectId, wf._id, patients(i), 0, false, setTimestamp))
      }
    )
  }

  def exportEncountersFromTemplateJson() = {
    val patients = PatientService.findAll
    val ids = patients.map(_._id.toString).toArray
    val template = Source.fromFile("docs/sample/encounter-template.json").mkString

    for(i <- 0 to ids.length - 1) {
      val id = ids(i)
      val contents = template.replaceAll("\\{PATIENT_ID\\}", id)
      Files.write(Paths.get(s"docs/sample/encounters/$id.json"), contents.getBytes(StandardCharsets.UTF_8))
    }
  }
}
