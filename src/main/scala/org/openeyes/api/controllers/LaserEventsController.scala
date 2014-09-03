package org.openeyes.api.controllers

import org.json4s.mongo.ObjectIdSerializer
import org.json4s.{DefaultFormats, Formats}
import org.openeyes.api.forms.LaserEventForm
import org.openeyes.api.services.LaserEventService
import org.openeyes.api.stacks.ApiStack
import org.scalatra.swagger.Swagger


class LaserEventsController(implicit val swagger: Swagger) extends ApiStack {

  protected val applicationDescription = "CRUD operations for LaserEvents"

  protected implicit val jsonFormats: Formats = DefaultFormats + new ObjectIdSerializer

  before() {
    contentType = formats("json")
  }

  get("/") {
    LaserEventService.findAll
  }

  get("/:id") {
    LaserEventService.find(params("id"))
  }

  post("/") {
    val resource = parsedBody.extract[LaserEventForm]
    LaserEventService.create(resource)
  }

  put("/:id") {
    val resource = parsedBody.extract[LaserEventForm]
    LaserEventService.update(params("id"), resource)
  }

}

