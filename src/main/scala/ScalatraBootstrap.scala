import java.io.File
import java.util.concurrent.{ExecutorService, Executors}
import javax.servlet.ServletContext

import org.openeyes.api.controllers._
import org.openeyes.api.controllers.workflow._
import org.openeyes.api.handlers.OctScan
import org.scalatra._

class ScalatraBootstrap extends LifeCycle {

  implicit val swagger = new OpenEyesSwagger

  val pool: ExecutorService = Executors.newFixedThreadPool(5)

  override def init(context: ServletContext) {
    context.mount(new ApiDocsController, "/api-docs")
    context.mount(new ElementController, "/Element", "Element")
    context.mount(new EncounterController, "/Encounter", "Encounter")
    context.mount(new PatientController, "/Patient", "Patient")
    context.mount(new TicketController, "/Ticket", "Ticket")
    context.mount(new WorkflowController, "/Workflow", "Workflow")

    try {
      pool.execute(new Handler())
    } catch {
      case e: Exception =>
        pool.shutdown()
        pool.execute(new Handler())
    }
  }

  override def destroy(context: ServletContext) {
    pool.shutdown()
  }

  class Handler() extends Runnable {
    def run() {
      val args: Array[String] = Array("-b", "OPENEYES:11112", "--directory", "tmp/DICOMFiles/")
      OctScan.main(args, (file: File) => {
        OctScan.process(file)
      })
    }
  }
}


