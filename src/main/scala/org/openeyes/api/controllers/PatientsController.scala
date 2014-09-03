package org.openeyes.api.controllers

import org.json4s.mongo.ObjectIdSerializer
import org.json4s.{DefaultFormats, Formats}
import org.openeyes.api.services.PatientService
import org.openeyes.api.stacks.ApiStack
import org.scalatra.swagger.Swagger
import org.scalatra.{NotFound, Ok}

/**
 * Created by stu on 02/09/2014.
 */
class PatientsController(implicit val swagger: Swagger) extends ApiStack {

  protected val applicationDescription = "The patients API."

  protected implicit val jsonFormats: Formats = DefaultFormats + new ObjectIdSerializer

  before() {
    contentType = formats("json")
  }

  get("/") {
    params.get("searchTerm") match {
      case Some(searchTerm) => PatientService.search(searchTerm)
      case None => PatientService.findAll
    }
  }

  get("/:id") {
    val id = params("id")
    PatientService.find(id) match {
      case Some(patient) => Ok(patient)
      case None => NotFound("No patient found for id '" + id + "'.")
    }
  }

}
