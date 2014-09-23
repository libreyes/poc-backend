package org.openeyes.api.controllers

import org.json4s.mongo.ObjectIdSerializer
import org.json4s.{FullTypeHints, DefaultFormats}
import org.openeyes.api.models.{ApiError, Element}
import org.openeyes.api.services.ElementService
import org.openeyes.api.stacks.ApiStack
import org.scalatra.swagger.Swagger

/**
 * Created by stu on 22/09/2014.
 */
class ElementController(implicit val swagger: Swagger) extends ApiStack {

  protected val applicationDescription = "The Element API."

  protected implicit val jsonFormats = new DefaultFormats {
    override val typeHintFieldName = "type"
    override val typeHints = FullTypeHints(List(classOf[Element]))
  } + new ObjectIdSerializer

  before() {
    contentType = formats("json")
  }

  val list = (apiOperation[List[Element]]("listElements")
    notes "Lists Elements for a given Patient ID, with optional filtering for Element Type and Date "
    parameters (
      queryParam[String]("patientId").description("A Patient ID to filter the Elements by").required,
      queryParam[String]("elementType").description("An optional full qualified Element Type to filter the Elements by").optional,
      queryParam[Long]("date").description("An optional Date timestamp to filter the Elements by").optional
    )
    summary "List Elements"
  )

  get("/", operation(list)) {
    val elementType = params.get("elementType")
    val timestamp = (params.get("date") match {
        case Some(d: String) => Some(d.toLong)
        case _ => None
      }
    )

    params.get("patientId") match {
      case Some(patientId) => ElementService.findAllForPatient(patientId, elementType, timestamp)
      case None => halt(400, ApiError("Patient ID not found"))
    }
  }
}
