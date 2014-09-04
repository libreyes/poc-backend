import javax.servlet.ServletContext

import org.openeyes.api.controllers._
import org.scalatra._

class ScalatraBootstrap extends LifeCycle {

  implicit val swagger = new OpeneyesSwagger

  override def init(context: ServletContext) {
    context.mount(new ResourcesApp, "/api-docs")
    context.mount(new LaserEventController, "/LaserEvent")
    context.mount(new LaserOperatorController, "/LaserOperator")
    context.mount(new LaserController, "/Laser")
    context.mount(new PatientController, "/Patient")
    context.mount(new SiteController, "/Site")
    context.mount(new ProcedureController, "/Procedure")
  }
}
