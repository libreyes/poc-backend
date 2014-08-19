import javax.servlet.ServletContext

import org.openeyes.api.controllers.ObservationsController
import org.scalatra._

class ScalatraBootstrap extends LifeCycle {
  override def init(context: ServletContext) {
    context.mount(new ObservationsController, "/observations/*")
  }
}
