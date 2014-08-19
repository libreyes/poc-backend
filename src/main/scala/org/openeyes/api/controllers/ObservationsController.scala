package org.openeyes.api.controllers

import org.json4s.{DefaultFormats, Formats}
import org.openeyes.api.forms.ObservationFormSupport
import org.openeyes.api.services.ObservationsService
import org.openeyes.api.stacks.ApiStack

class ObservationsController extends ApiStack with ObservationFormSupport {


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
