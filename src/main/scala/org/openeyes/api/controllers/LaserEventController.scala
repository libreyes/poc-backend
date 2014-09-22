package org.openeyes.api.controllers

import org.json4s.mongo.ObjectIdSerializer
import org.json4s.{DefaultFormats, Formats}
import org.openeyes.api.forms.LaserEventForm
import org.openeyes.api.models.{ApiError, LaserEvent}
import org.openeyes.api.services.LaserEventService
import org.openeyes.api.stacks.ApiStack
import org.scalatra.swagger.{DataType, ParamType, Parameter, Swagger}
import org.scalatra.{NotFound, Ok}


class LaserEventController(implicit val swagger: Swagger) extends ApiStack {

  protected val applicationDescription = "The LaserEvent API"

  protected implicit val jsonFormats: Formats = DefaultFormats + new ObjectIdSerializer

  before() {
    contentType = formats("json")
  }

  val list = (apiOperation[List[LaserEvent]]("listLaserEvents")
    notes "Lists all known LaserEvents"
    parameters (
      queryParam[String]("patientId").description("An optional Patient ID to filter the LaserEvents by").optional
    )
    summary "List LaserEvents"
    )

  get("/", operation(list)) {
    params.get("patientId") match {
      case Some(patientId) => LaserEventService.findAllForPatient(patientId)
      case None => LaserEventService.findAll
    }
  }

  val get = (apiOperation[LaserEvent]("getLaserEvent")
    notes "Get a LaserEvent by ID"
    parameters (
      pathParam[String]("id").description("The ID of the LaserEvent to retrieve").required
    )
    summary "Get LaserEvent"
    )

  get("/:id", operation(get)) {
    val id = params("id")
    LaserEventService.find(id) match {
      case Some(laserEvent) => Ok(laserEvent)
      case None => NotFound(ApiError("No laser event found for id '" + id + "'."))
    }
  }

  val post = (apiOperation[LaserEvent]("createLaserEvent")
    notes "Create a LaserEvent"
    parameters (
      bodyParam[LaserEventForm].description("The LaserEvent content").required
    )
    summary "Create LaserEvent"
    )

  post("/", operation(post)) {
    val resource = parsedBody.extract[LaserEventForm]
    LaserEventService.create(resource)
  }

  val put = (apiOperation[LaserEvent]("updateLaserEvent")
    notes "Update a LaserEvent"
    parameters(
      pathParam[String]("id").description("The ID of the LaserEvent to update").required,
      bodyParam[LaserEventForm].description("The LaserEvent content").required
    )
    summary "Update LaserEvent"
    )

  put("/:id", operation(put)) {
    val resource = parsedBody.extract[LaserEventForm]
    LaserEventService.update(params("id"), resource)
  }

}

