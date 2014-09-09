import javax.servlet.ServletContext

import org.openeyes.api.controllers._
import org.openeyes.api.data.init.DatabaseAccess
import org.scalatra._

class ScalatraBootstrap extends LifeCycle {

  implicit val swagger = new OpeneyesSwagger

  DatabaseAccess.createDatasource

  override def init(context: ServletContext) {
    context.mount(new ResourcesApp, "/api-docs")
    context.mount(new LaserEventController, "/LaserEvent", "LaserEvent")
    context.mount(new LaserOperatorController, "/LaserOperator", "LaserOperator")
    context.mount(new LaserController, "/Laser", "Laser")
    context.mount(new PatientController, "/Patient", "Patient")
    context.mount(new ProcedureController, "/Procedure", "Procedure")
    context.mount(new SiteController, "/Site", "Site")
  }

  override def destroy(context: ServletContext) {
    super.destroy(context)
    DatabaseAccess.closeDbConnection
  }
}
