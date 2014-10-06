package org.openeyes.api.controllers.workflow

import org.openeyes.api.forms.workflow.TicketForm
import org.openeyes.api.models.ApiError
import org.openeyes.api.models.workflow.Ticket
import org.openeyes.api.stacks.ApiStack
import org.scalatra.swagger.Swagger

/**
 * Created by stu on 23/09/2014.
 */
class TicketController(implicit val swagger: Swagger) extends ApiStack {

  protected val applicationDescription = "The Ticket API"

  val list = (apiOperation[List[Ticket]]("listTickets")
    notes "Lists Tickets for a given Workflow ID, with optional filtering for step and completed status"
    parameters (
    queryParam[String]("workflowId").description("A Workflow ID to filter the Tickets by").required,
    queryParam[Int]("stepIndex").description("An optional step number to filter the Tickets by").optional,
    queryParam[Boolean]("includeCompleted").description("An optional completed value to filter the Tickets by").optional
    )
    summary "List Tickets"
    )

  get("/", operation(list)) {
    val stepIndex = (params.get("stepIndex") match {
      case Some(d: String) => Some(d.toInt)
      case _ => None
    })

    val includeCompleted = (params.get("includeCompleted") match {
      case Some(i: String) => i.toBoolean
      case _ => false
    })

    params.get("workflowId") match {
      case Some(workflowId) => env.ticketService.findAllForWorkflow(workflowId, stepIndex, includeCompleted)
      case None => halt(400, ApiError("workflowId is required"))
    }
  }

  val get = (apiOperation[Ticket]("getTicket")
    notes "Get an Ticket by ID"
    parameters(
    pathParam[String]("id").description("The ID of the Ticket to retrieve").required
    )
    summary "Get Ticket"
    )

  get("/:id", operation(get)) {
    val id = params("id")
    env.ticketService.find(id)
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
    env.ticketService.create(form)
  }
}
