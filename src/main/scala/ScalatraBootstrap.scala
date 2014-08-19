import javax.servlet.ServletContext

import org.openeyes.api.controllers.{ResourcesApp, OpeneyesSwagger, ObservationsController}
import org.scalatra._

class ScalatraBootstrap extends LifeCycle {

  implicit val swagger = new OpeneyesSwagger


  override def init(context: ServletContext) {
    context.mount (new ResourcesApp, "/api-docs")
    context.mount(new ObservationsController, "/observations", "observations")
  }
}
