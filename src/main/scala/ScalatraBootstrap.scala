import javax.servlet.ServletContext

import org.openeyes.api.controllers._
import org.scalatra._

class ScalatraBootstrap extends LifeCycle {

  implicit val swagger = new OpeneyesSwagger

  override def init(context: ServletContext) {
    context.mount (new ResourcesApp, "/api-docs")
    context.mount(new LaserEventsController, "/laser-events", "laser-events")
    context.mount(new PatientsController, "/patients", "patients")
    context.mount(new SitesController, "/sites", "sites")
  }
}
