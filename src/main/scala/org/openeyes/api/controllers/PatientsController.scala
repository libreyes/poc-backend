package org.openeyes.api.controllers

import org.json4s.mongo.ObjectIdSerializer
import org.json4s.{DefaultFormats, Formats}
import org.openeyes.api.models.{ApiError, Patient}
import org.openeyes.api.services.PatientService
import org.openeyes.api.stacks.ApiStack
import org.scalatra.swagger.{DataType, ParamType, Parameter, Swagger}
import org.scalatra.{NotFound, Ok}

/**
 * Created by stu on 02/09/2014.
 */
class PatientsController(implicit val swagger: Swagger) extends ApiStack {

  protected val applicationDescription = "The Patient API"

  protected implicit val jsonFormats: Formats = DefaultFormats + new ObjectIdSerializer

  before() {
    contentType = formats("json")
  }

  val list = (apiOperation[List[Patient]]("listPatients")
    notes "Lists all known Patients"
    parameters(
      Parameter("searchTerm", DataType.String, Some("An optional search term value to filter Patients by"), None, ParamType.Query, required = false)
    )
    summary "List Patients"
  )

  get("/", operation(list)) {
    params.get("searchTerm") match {
      case Some(searchTerm) => PatientService.search(searchTerm)
      case None => PatientService.findAll
    }
  }

  val get = (apiOperation[Patient]("getPatient")
    notes "Get a Patient by ID"
    parameters(
      Parameter("id", DataType.String, Some("The ID of the Patient to retrieve"), None, ParamType.Path, required = true)
    )
    summary "Get Patient"
  )

  get("/:id", operation(get)) {
    val id = params("id")
    PatientService.find(id) match {
      case Some(patient) => Ok(patient)
      case None => NotFound(ApiError("No patient found for id '" + id + "'."))
    }
  }

}
