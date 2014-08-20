package org.openeyes.api.controllers

import org.json4s.{DefaultFormats, Formats}
import org.json4s.JsonDSL._
import org.openeyes.api.forms.ObservationFormSupport
import org.openeyes.api.models.Observation
import org.openeyes.api.services.ObservationService
import org.openeyes.api.stacks.ApiStack
import org.scalatra.swagger.{ParamType, DataType, Parameter, Swagger}

class ObservationsController(implicit val swagger: Swagger) extends ApiStack with ObservationFormSupport {

  protected val applicationDescription = "The OpenEyes API. It exposes operations for listing of " +
    "Observations, and creation of Observations."

  protected implicit val jsonFormats: Formats = DefaultFormats

  before() {
    contentType = formats("json")
  }


  val listObservations =
    (apiOperation[List[Observation]]("listObservations")
      summary("List all Observations")
      notes("Shows all known Observations"))

  get("/", operation(listObservations)) {
    val observations = ObservationService.listAll.toSeq
    val ast = observations.map { obs =>
      (
        ("id" -> obs._id.toString) ~
          ("weight" -> obs.weight.grams)
        )
    }
    compact(render(ast))
  }

  val createObservation =
    (apiOperation[Observation]("createObservation")
      summary("Create a new Observations")
      notes("None at present")
      parameters(
        Parameter("weight", DataType.Int, Some("The observed weight"), None, ParamType.Form, None, required = true))
      )


  post("/", observationForm) { form: ObservationForm =>
    val observation = ObservationService.create(form.weight)
    observation
  }

}
