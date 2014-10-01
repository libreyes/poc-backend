import java.io.File
import java.util.concurrent.{ExecutorService, Executors}
import javax.imageio.ImageIO
import javax.imageio.stream.FileImageInputStream
import javax.servlet.ServletContext

import org.dcm4che3.data.Tag
import org.dcm4che3.imageio.plugins.dcm._

import org.openeyes.api.StoreSCP
import org.openeyes.api.controllers._
import org.openeyes.api.controllers.workflow._
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

      StoreSCP.main(args, (file: File) => {

//        val reader = new DicomImageReaderSpi().createReaderInstance()
//        val input = new FileImageInputStream(file)
//        reader.setInput(input)

        val reader = ImageIO.getImageReadersByFormatName("DICOM").next()
        val param = reader.getDefaultReadParam().asInstanceOf[DicomImageReadParam]
        val inputStream = ImageIO.createImageInputStream(file)
        reader.setInput(inputStream)

        val metaData = reader.getStreamMetadata.asInstanceOf[DicomMetaData]

        println("LOOK HERE!!!")
        println(metaData.getFileMetaInformation)

        println("NUMBER OF FRAMES? " + Tag.NumberOfFrames)
        println("PIXEL DATA: " + Tag.PixelData)

//        val numberOfImages = reader.getNumImages(true)
//
//        println("LOOK HERE!!!")
//        println("numberOfImages: " + numberOfImages)
//
//        for(i <- 1 to numberOfImages) {
//          val buffer = reader.read(i, param)
//          val jpegFile = new File(s"tmp/DICOMFiles/$i.jpg")
//          ImageIO.write(buffer, "jpeg", jpegFile)
//        }
      })
    }
  }
}


