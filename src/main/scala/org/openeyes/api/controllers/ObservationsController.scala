package org.openeyes.api.controllers

import org.json4s.{DefaultFormats, Formats}
import org.openeyes.api.forms.ObservationFormSupport
import org.openeyes.api.services.ObservationsService
import org.scalatra.ScalatraServlet
import org.scalatra.i18n.I18nSupport
import org.scalatra.json._


class ObservationsController extends ScalatraServlet with JacksonJsonSupport with ObservationFormSupport with I18nSupport {


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
