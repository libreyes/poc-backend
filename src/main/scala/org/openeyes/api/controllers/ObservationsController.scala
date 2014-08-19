package org.openeyes.api.controllers

import org.json4s.{DefaultFormats, Formats}
import org.openeyes.api.forms.ObservationFormSupport
import org.openeyes.api.services.ObservationsService
import org.openeyes.api.stacks.ApiStack
import org.scalatra.swagger.Swagger

class ObservationsController(implicit val swagger: Swagger) extends ApiStack with ObservationFormSupport {


  protected implicit val jsonFormats: Formats = DefaultFormats

  before() {
    contentType = formats("json")
  }


  get("/") {
    val observations = ObservationsService.listAll
  }

  post("/", observationForm) { form: ObservationForm =>
    val observation = ObservationsService.create(form.weight)
    observation
  }

}
