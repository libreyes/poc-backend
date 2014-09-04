package org.openeyes.api.controllers

import org.json4s.mongo.ObjectIdSerializer
import org.json4s.{DefaultFormats, Formats}
import org.openeyes.api.forms.LaserEventForm
import org.openeyes.api.models.{ApiError, LaserEvent}
import org.openeyes.api.services.LaserEventService
import org.openeyes.api.stacks.ApiStack
import org.scalatra.swagger.{DataType, ParamType, Parameter, Swagger}
import org.scalatra.{NotFound, Ok}


class LaserEventsController(implicit val swagger: Swagger) extends ApiStack {

  protected val applicationDescription = "The LaserEvent API"

  protected implicit val jsonFormats: Formats = DefaultFormats + new ObjectIdSerializer

  before() {
    contentType = formats("json")
  }

  val list = (apiOperation[List[LaserEvent]]("listLaserEvents")
    notes "Lists all known LaserEvents"
    summary "List LaserEvents"
    )

  get("/", operation(list)) {
    LaserEventService.findAll
  }

  val get = (apiOperation[LaserEvent]("getLaserEvent")
    notes "Get a LaserEvent by ID"
    parameters (
    Parameter("id", DataType.String, Some("The ID of the LaserEvent to retrieve"), None, ParamType.Path, required = true)
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
      Parameter("body", DataType[LaserEventForm], Some("The LaserEvent content"), None, ParamType.Body, required = true)
    )
    summary "Create LaserEvent"
    )

  post("/", operation(post)) {
    val resource = parsedBody.extract[LaserEventForm]
    LaserEventService.create(resource)
  }

  val put = (apiOperation[LaserEvent]("createLaserEvent")
    notes "Update a LaserEvent"
    parameters (
      Parameter("id", DataType.String, Some("The ID of the LaserEvent to update"), None, ParamType.Path, required = true),
      Parameter("body", DataType[LaserEventForm], Some("The LaserEvent content"), None, ParamType.Body, required = true)
    )
    summary "Update LaserEvent"
    )

  put("/:id", operation(put)) {
    val resource = parsedBody.extract[LaserEventForm]
    LaserEventService.update(params("id"), resource)
  }

}

