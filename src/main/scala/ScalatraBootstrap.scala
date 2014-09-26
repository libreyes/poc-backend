import javax.servlet.ServletContext

import org.openeyes.api.controllers._
import org.openeyes.api.controllers.workflow._
import org.scalatra._

class ScalatraBootstrap extends LifeCycle {

  implicit val swagger = new OpenEyesSwagger

  override def init(context: ServletContext) {
    context.mount(new ApiDocsController, "/api-docs")
    context.mount(new ElementController, "/Element", "Element")
    context.mount(new EncounterController, "/Encounter", "Encounter")
    context.mount(new PatientController, "/Patient", "Patient")
    context.mount(new TicketController, "/Ticket", "Ticket")
    context.mount(new WorkflowController, "/Workflow", "Workflow")
  }
}
