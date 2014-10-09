import java.io.File
import java.util.concurrent.{ExecutorService, Executors}
import javax.servlet.ServletContext

import org.openeyes.api.controllers._
import org.openeyes.api.controllers.workflow._
import org.openeyes.api.handlers.OctScan
import org.scalatra._
import org.springframework.beans.factory.config.AutowireCapableBeanFactory
import org.springframework.context.annotation.AnnotationConfigApplicationContext

class ScalatraBootstrap extends LifeCycle {

  val appContext = new AnnotationConfigApplicationContext("org.openeyes.api.controllers", "org.openeyes.api.models", "org.openeyes.api.services")

  val pool: ExecutorService = Executors.newFixedThreadPool(5)

  override def init(context: ServletContext) {
    context.mount(appContext.getBean(classOf[ApiDocsController]), "/api-docs")
    context.mount(appContext.getBean(classOf[ElementController]), "/Element", "Element")
    context.mount(appContext.getBean(classOf[EncounterController]), "/Encounter", "Encounter")
    context.mount(appContext.getBean(classOf[PatientController]), "/Patient", "Patient")
    context.mount(appContext.getBean(classOf[TicketController]), "/Ticket", "Ticket")
    context.mount(appContext.getBean(classOf[WorkflowController]), "/Workflow", "Workflow")

    // TODO: Need to fix this so we can use the auto compile.
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

      appContext.getBean(classOf[AutowireCapableBeanFactory]).autowireBean(OctScan)

      OctScan.main(args, (file: File) => {
        OctScan.process(file)
      })
    }
  }
}


