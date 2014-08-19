import org.openeyes.api._
import org.openeyes.api.controllers.ObservationsController
import org.scalatra._
import javax.servlet.ServletContext

class ScalatraBootstrap extends LifeCycle {
  override def init(context: ServletContext) {
    context.mount(new ObservationsController, "/observations/*")
  }
}
