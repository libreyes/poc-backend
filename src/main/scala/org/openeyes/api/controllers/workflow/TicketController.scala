package org.openeyes.api.controllers.workflow

import org.json4s.mongo.ObjectIdSerializer
import org.json4s.{DefaultFormats, Formats}
import org.openeyes.api.forms.workflow.TicketForm
import org.openeyes.api.models.ApiError
import org.openeyes.api.models.workflow.Ticket
import org.openeyes.api.services.workflow.TicketService
import org.openeyes.api.stacks.ApiStack
import org.scalatra.swagger.Swagger

/**
 * Created by stu on 23/09/2014.
 */
class TicketController(implicit val swagger: Swagger) extends ApiStack {

  protected val applicationDescription = "The Ticket API"

  protected implicit val jsonFormats: Formats = DefaultFormats + new ObjectIdSerializer

  before() {
    contentType = formats("json")
  }

  val list = (apiOperation[List[Ticket]]("listTickets")
    notes "Lists Tickets for a given Workflow ID, with optional filtering for step and completed status"
    parameters (
    queryParam[String]("workflowId").description("A Workflow ID to filter the Tickets by").required,
    queryParam[Int]("step").description("An optional step number to filter the Tickets by").optional,
    queryParam[Boolean]("completed").description("An optional completed value to filter the Tickets by").optional
    )
    summary "List Tickets"
    )

  get("/", operation(list)) {
    params.get("workflowId") match {
      case Some(workflowId) => TicketService.findAllForWorkflow(workflowId)
      case None => halt(400, ApiError("Workflow ID not found"))
    }
  }

  val post = (apiOperation[Ticket]("createTicket")
    notes "Create an Encounter"
    parameters(
    bodyParam[TicketForm].description("The Ticket content").required
    )
    summary "Create Ticket"
    )

  post("/", operation(post)) {
    val form = parsedBody.extract[TicketForm]
    TicketService.create(form)
  }
}
