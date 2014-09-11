package org.openeyes.api.controllers

import org.json4s.mongo.ObjectIdSerializer
import org.json4s.{DefaultFormats, Formats}
import org.openeyes.api.models.{ApiError, Procedure}
import org.openeyes.api.services.ProcedureService
import org.openeyes.api.stacks.ApiStack
import org.scalatra.swagger.{DataType, ParamType, Parameter, Swagger}
import org.scalatra.{NotFound, Ok}

/**
 * Created by stu on 04/09/2014.
 */
class ProcedureController(implicit val swagger: Swagger) extends ApiStack {

  protected val applicationDescription = "The Procedure API."

  protected implicit val jsonFormats: Formats = DefaultFormats + new ObjectIdSerializer

  before() {
    contentType = formats("json")
  }

  val list = (apiOperation[List[Procedure]]("listProcedure")
    notes "Lists all known Procedures"
    summary "List Procedures"
    )

  get("/", operation(list)) {
    ProcedureService.findAll
  }

  val get = (apiOperation[Procedure]("getProcedure")
    notes "Get a Procedure by ID"
    parameters (
      pathParam[String]("id").description("The ID of the Procedure to retrieve").required
    )
    summary "Get Procedure"
  )

  get("/:id", operation(get)) {
    val id = params("id")
    ProcedureService.find(id) match {
      case Some(procedure) => Ok(procedure)
      case None => NotFound(ApiError("No procedure found for id '" + id + "'."))
    }
  }

}
