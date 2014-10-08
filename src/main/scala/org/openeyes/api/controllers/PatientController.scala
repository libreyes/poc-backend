package org.openeyes.api.controllers

import org.json4s.mongo.ObjectIdSerializer
import org.json4s.{DefaultFormats, Formats}
import org.openeyes.api.models.{ApiError, Patient}
import org.openeyes.api.services.PatientService
import org.openeyes.api.stacks.ApiStack
import org.scalatra.swagger.{DataType, ParamType, Parameter, Swagger}
import org.scalatra.{NotFound, Ok}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller

/**
 * Created by stu on 02/09/2014.
 */
@Controller
class PatientController @Autowired() (val swagger: Swagger, patientService: PatientService) extends ApiStack {

  protected val applicationDescription = "The Patient API"

  override protected implicit val jsonFormats: Formats = DefaultFormats + new ObjectIdSerializer

  val list = (apiOperation[List[Patient]]("listPatients")
    notes "Lists all known Patients"
    parameters (
    Parameter("searchTerm", DataType.String, Some("An optional search term value to filter Patients by"), None, ParamType.Query, required = false)
    )
    summary "List Patients"
    )

  get("/", operation(list)) {
    params.get("searchTerm") match {
      case Some(searchTerm) => patientService.search(searchTerm)
      case None => patientService.findAll
    }
  }

  val get = (apiOperation[Patient]("getPatient")
    notes "Get a Patient by ID"
    parameters (
      pathParam[String]("id").description("The ID of the Patient to retrieve").required
    )
    summary "Get Patient"
    )

  get("/:id", operation(get)) {
    val id = params("id")
    patientService.find(id) match {
      case Some(patient) => Ok(patient)
      case None => NotFound(ApiError("No patient found for id '" + id + "'."))
    }
  }

}
