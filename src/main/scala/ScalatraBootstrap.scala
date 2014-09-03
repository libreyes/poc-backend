import javax.servlet.ServletContext

import org.openeyes.api.controllers._
import org.scalatra._

class ScalatraBootstrap extends LifeCycle {

  implicit val swagger = new OpeneyesSwagger

  override def init(context: ServletContext) {
    context.mount(new ResourcesApp, "/api-docs")
    context.mount(new LasersController, "/laser", "lasers")
    context.mount(new LaserEventsController, "/laser-event", "laserEvents")
    context.mount(new LaserOperatorsController, "/laser-operator", "laserOperators")
    context.mount(new PatientsController, "/patient", "patients")
    context.mount(new SitesController, "/site", "sites")
  }
}
