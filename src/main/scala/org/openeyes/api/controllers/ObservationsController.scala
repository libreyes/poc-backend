package org.openeyes.api.controllers

import org.json4s.mongo.ObjectIdSerializer
import org.json4s.{DefaultFormats, Formats}
import org.openeyes.api.forms.ObservationFormSupport
import org.openeyes.api.models.Observation
import org.openeyes.api.services.ObservationService
import org.openeyes.api.stacks.ApiStack
import org.openeyes.api.views.observations.ObservationList
import org.scalatra.swagger.{DataType, ParamType, Parameter, Swagger}


class ObservationsController(implicit val swagger: Swagger) extends ApiStack with ObservationFormSupport {

  protected val applicationDescription = "The OpenEyes API. It exposes operations for listing of " +
    "Observations, and creation of Observations."

  protected implicit val jsonFormats: Formats = DefaultFormats + new ObjectIdSerializer

  before() {
    contentType = formats("json")
  }


  val listObservations =
    (apiOperation[ObservationList]("listObservations")
      summary ("List all Observations")
      notes ("Shows all known Observations"))

  get("/", operation(listObservations)) {
    val observations = ObservationService.listAll
    // ObservationList(observations) // an experiment in view handling, save for later.
    observations
  }

  val createObservation =
    (apiOperation[Observation]("createObservation")
      summary ("Create a new Observations")
      notes ("None at present")
      parameters (
      Parameter("weight", DataType.Int, Some("The observed weight"), None, ParamType.Form, None, required = true))
      )


  post("/", observationForm) { form: ObservationForm =>
    val observation = ObservationService.create(form.weight)
    observation
  }
}
