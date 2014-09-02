package org.openeyes.api.controllers

import org.json4s.mongo.ObjectIdSerializer
import org.json4s.{DefaultFormats, Formats}
import org.openeyes.api.forms.LaserEventFormSupport
import org.openeyes.api.services.LaserEventService
import org.openeyes.api.stacks.ApiStack
import org.scalatra.swagger.Swagger


class LaserEventsController(implicit val swagger: Swagger) extends ApiStack with LaserEventFormSupport {

  protected val applicationDescription = "CRUD operations for LaserEvents"

  protected implicit val jsonFormats: Formats = DefaultFormats + new ObjectIdSerializer

  before() {
    contentType = formats("json")
  }

  get("/") {
    LaserEventService.findAll
  }

  post("/", laserEventForm) { form: LaserEventForm =>
    LaserEventService.create()
  }

}

