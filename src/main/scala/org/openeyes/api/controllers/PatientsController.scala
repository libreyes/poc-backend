package org.openeyes.api.controllers

import org.json4s.mongo.ObjectIdSerializer
import org.json4s.{DefaultFormats, Formats}
import org.openeyes.api.forms.PatientFormSupport
import org.openeyes.api.services.PatientsService
import org.openeyes.api.stacks.ApiStack
import org.scalatra.swagger.Swagger
import org.scalatra.{NotFound, Ok}

/**
 * Created by stu on 02/09/2014.
 */
class PatientsController(implicit val swagger: Swagger) extends ApiStack with PatientFormSupport {

  protected val applicationDescription = "The patients API."

  protected implicit val jsonFormats: Formats = DefaultFormats + new ObjectIdSerializer

  before() {
    contentType = formats("json")
  }

  get("/", patientForm) { form: PatientForm =>
    if (form.searchTerm != null) {
      PatientsService.search(form.searchTerm)
    } else {
      PatientsService.findAll
    }
  }

  get("/:id") {
    val id = params("id")
    PatientsService.find(id) match {
      case Some(patient) => Ok(patient)
      case None => NotFound("No patient found for id '" + id + "'.")
    }
  }

}
