package org.openeyes.api.controllers.workflow

import org.json4s.mongo.ObjectIdSerializer
import org.json4s.{DefaultFormats, FullTypeHints}
import org.openeyes.api.forms.workflow.WorkflowForm
import org.openeyes.api.models.workflow.{Component, Workflow}
import org.openeyes.api.services.workflow.WorkflowService
import org.openeyes.api.stacks.ApiStack
import org.scalatra.swagger.Swagger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller

/**
 * Created by jamie on 15/09/2014.
 */
@Controller
class WorkflowController @Autowired() (val swagger: Swagger, workflowService: WorkflowService) extends ApiStack {

  protected val applicationDescription = "The Workflow API."

  override protected implicit val jsonFormats = new DefaultFormats {
    override val typeHintFieldName = "type"
    override val typeHints = FullTypeHints(List(classOf[Component]))
  } + new ObjectIdSerializer

  val list = (apiOperation[List[Workflow]]("listWorkflows")
    notes "Lists all known Workflows"
    parameters(
    )
    summary "List Workflows"
    )

  get("/", operation(list)) {
    workflowService.findAll
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
    workflowService.find(id)
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
    workflowService.create(resource)
  }
}
