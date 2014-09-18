package org.openeyes.api.controllers.workflow

import org.json4s.mongo.ObjectIdSerializer
import org.json4s.{DefaultFormats, Formats}
import org.openeyes.api.forms.workflow.WorkflowQueueForm
import org.openeyes.api.models.workflow.WorkflowQueue
import org.openeyes.api.services.workflow.WorkflowQueueService
import org.openeyes.api.stacks.ApiStack
import org.scalatra.swagger.Swagger

/**
 * Created by dave on 17/09/2014.
 */
class WorkflowQueueController(implicit val swagger: Swagger) extends ApiStack {

  protected val applicationDescription = "The Workflow Queue API"

  protected implicit val jsonFormats: Formats = DefaultFormats + new ObjectIdSerializer

  before() {
    contentType = formats("json")
  }

  get("/") {
    params.get("roleId") match {
      case Some(roleId) => WorkflowQueueService.findAllForRole(roleId)
      case None => WorkflowQueueService.findAll
    }
  }


  val get = (apiOperation[WorkflowQueue]("getWorkflowQueue")
    notes "Get the contents of a WorkflowQueue by ID"
    parameters (
    pathParam[String]("id").description("The ID of the WorkflowQueue to retrieve").required
    )
    summary "Get WorkflowQueue"
    )

  get("/:id", operation(get)) {
    val id = params("id")
    WorkflowQueueService.find(id)
  }

  post("/") {
    val resource = parsedBody.extract[WorkflowQueueForm]
    WorkflowQueueService.create(resource)
  }

}
