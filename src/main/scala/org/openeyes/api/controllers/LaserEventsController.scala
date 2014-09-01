package org.openeyes.api.controllers

import org.json4s.mongo.ObjectIdSerializer
import org.json4s.{DefaultFormats, Formats}
import org.openeyes.api.forms.FormValidators.LaserEventForm
import org.openeyes.api.forms.LaserEventFormSupport
import org.openeyes.api.services.LaserEventService
import org.openeyes.api.stacks.ApiStack
import org.scalatra.swagger.Swagger


class LaserEventsController(implicit val swagger: Swagger) extends ApiStack with LaserEventFormSupport {

  protected val applicationDescription = "The OpenEyes API. It exposes operations for listing of " +
    "Observations, and creation of Observations."

  protected implicit val jsonFormats: Formats = DefaultFormats + new ObjectIdSerializer

  before() {
    contentType = formats("json")
  }

  get("/") {
    LaserEventService.findAll
  }


  get("/create", laserEventForm) { form: LaserEventForm =>
    LaserEventService.create()
  }
}
