import javax.servlet.ServletContext

import org.openeyes.api.controllers._
import org.scalatra._

class ScalatraBootstrap extends LifeCycle {

  implicit val swagger = new OpeneyesSwagger

  override def init(context: ServletContext) {
    context.mount(new ResourcesApp, "/api-docs")
    context.mount(new LaserEventsController, "/LaserEvent")
    context.mount(new LaserOperatorsController, "/LaserOperator")
    context.mount(new LasersController, "/Laser")
    context.mount(new PatientsController, "/Patient")
    context.mount(new SitesController, "/Site")
    context.mount(new ProceduresController, "/Procedure")
  }
}
