import javax.servlet.ServletContext

import jp.sf.amateras.scalatra.forms.ValidationJavaScriptProvider
import org.openeyes.api.controllers.{ResourcesApp, OpeneyesSwagger, LaserEventsController}
import org.scalatra._

class ScalatraBootstrap extends LifeCycle {

  implicit val swagger = new OpeneyesSwagger


  override def init(context: ServletContext) {
    context.mount(new LaserEventsController, "/laser-events", "laser-events")
    context.mount (new ResourcesApp, "/api-docs")
    context.mount(new ValidationJavaScriptProvider, "/assets/js/*")
  }
}
