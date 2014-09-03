import javax.servlet.ServletContext

import org.openeyes.api.controllers._
import org.scalatra._

class ScalatraBootstrap extends LifeCycle {

  implicit val swagger = new OpeneyesSwagger

  override def init(context: ServletContext) {
    context.mount(new ResourcesApp, "/api-docs")
    context.mount(new LaserEventsController, "/laser-event", "laser-event")
    context.mount(new LaserOperatorsController, "/laser-operator", "laser-operator")
    context.mount(new LasersController, "/laser", "laser")
    context.mount(new PatientsController, "/patient", "patient")
    context.mount(new SitesController, "/site", "site")
  }
}
