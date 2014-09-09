import javax.servlet.ServletContext

import com.mchange.v2.c3p0.ComboPooledDataSource
import org.openeyes.api.controllers._
import org.scalatra._
import scala.slick.session.Database

class ScalatraBootstrap extends LifeCycle {

  implicit val swagger = new OpeneyesSwagger

  val cpds = new ComboPooledDataSource

  override def init(context: ServletContext) {
    val db = Database.forDataSource(cpds)

    context.mount(new ResourcesApp, "/api-docs")
    context.mount(new LaserEventController, "/LaserEvent", "LaserEvent")
    context.mount(new LaserOperatorController, "/LaserOperator", "LaserOperator")
    context.mount(new LaserController, "/Laser", "Laser")
    context.mount(new PatientController, "/Patient", "Patient")
    context.mount(new ProcedureController, "/Procedure", "Procedure")
    context.mount(new SiteController, "/Site", "Site")
    context.mount(new SlickTrialController(db), "/db/*")
  }

  private def closeDbConnection() {
    cpds.close
  }

  override def destroy(context: ServletContext) {
    super.destroy(context)
    closeDbConnection
  }
}
