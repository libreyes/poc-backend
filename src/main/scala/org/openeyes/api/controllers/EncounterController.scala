package org.openeyes.api.controllers

import org.json4s.mongo.ObjectIdSerializer
import org.json4s.{FullTypeHints, DefaultFormats}
import org.openeyes.api.forms.EncounterForm
import org.openeyes.api.models.{Encounter, Element}
import org.openeyes.api.services.EncounterService
import org.openeyes.api.stacks.ApiStack
import org.scalatra.swagger.Swagger


/**
 * Created by jamie on 15/09/2014.
 */
class EncounterController(implicit val swagger: Swagger) extends ApiStack {

  protected val applicationDescription = "The Encounter API."

  protected implicit val jsonFormats = new DefaultFormats {
    override val typeHintFieldName = "type"
    override val typeHints = FullTypeHints(List(classOf[Element]))
  } + new ObjectIdSerializer

  before() {
    contentType = formats("json")
  }

  val list = (apiOperation[List[Encounter]]("listEncounters")
    notes "Lists all known Encounters"
    parameters(
    queryParam[String]("patientId").description("An optional Patient ID to filter the Encounters by").optional
    )
    summary "List Encounters"
    )

  get("/", operation(list)) {
    params.get("patientId") match {
      case Some(patientId) => EncounterService.findAllForPatient(patientId)
      case None => EncounterService.findAll
    }
  }

  val get = (apiOperation[Encounter]("getEncounter")
    notes "Get an Encounter by ID"
    parameters(
    pathParam[String]("id").description("The ID of the Encounter to retrieve").required
    )
    summary "Get Encounter"
    )

  get("/:id", operation(get)) {
    val id = params("id")
    EncounterService.find(id)
  }

  val post = (apiOperation[Encounter]("createEncounter")
    notes "Create an Encounter"
    parameters(
    bodyParam[EncounterForm].description("The Encounter content").required
    )
    summary "Create Encounter"
    )

  post("/", operation(post)) {
    val resource = parsedBody.extract[EncounterForm]
    EncounterService.create(resource)
  }

}
