package org.openeyes.api.controllers.workflow

import org.json4s.DefaultFormats
import org.json4s.mongo.ObjectIdSerializer
import org.openeyes.api.forms.workflow.WorkflowForm
import org.openeyes.api.models.workflow.Workflow
import org.openeyes.api.services.workflow.WorkflowService
import org.openeyes.api.stacks.ApiStack
import org.scalatra.swagger.Swagger

/**
 * Created by jamie on 15/09/2014.
 */
class WorkflowController(implicit val swagger: Swagger) extends ApiStack {

  protected val applicationDescription = "The Workflow API."

  protected implicit val jsonFormats = DefaultFormats + new ObjectIdSerializer

  before() {
    contentType = formats("json")
  }

  val list = (apiOperation[List[Workflow]]("listWorkflows")
    notes "Lists all known Workflows"
    parameters(
    )
    summary "List Workflows"
    )

  get("/", operation(list)) {
    WorkflowService.findAll
  }

  val get = (apiOperation[Workflow]("getWorkflow")
    notes "Get an Workflow by ID"
    parameters(
      pathParam[String]("id").description("The ID of the Workflow to retrieve").required
    )
    summary "Get Workflow"
    )

  get("/:id", operation(get)) {
    val id = params("id")
    WorkflowService.find(id)
  }

  val post = (apiOperation[Workflow]("createWorkflow")
    notes "Create an Workflow"
    parameters(
      bodyParam[WorkflowForm].description("The Workflow content").required
    )
    summary "Create Workflow"
    )

  post("/", operation(post)) {
    val resource = parsedBody.extract[WorkflowForm]
    WorkflowService.create(resource)
  }

}
