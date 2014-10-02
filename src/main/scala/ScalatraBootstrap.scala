import java.awt.image.BufferedImage
import java.io.File
import java.util.concurrent.{ExecutorService, Executors}
import javax.imageio.ImageIO
import javax.imageio.stream.FileImageInputStream
import javax.servlet.ServletContext

import org.dcm4che3.data.{Attributes, Tag}
import org.dcm4che3.imageio.plugins.dcm._
import org.dcm4che3.io.DicomInputStream
import org.im4java.core.{MontageCmd, ConvertCmd, IMOperation}

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

        val dis = new DicomInputStream(file)
        val attr = new Attributes()
        dis.readAttributes(attr, -1, -1)
        val patientId = attr.getString(Tag.PatientID)
        dis.close()

        val reader = ImageIO.getImageReadersByFormatName("DICOM").next()
        val param = reader.getDefaultReadParam().asInstanceOf[DicomImageReadParam]
        val inputStream = ImageIO.createImageInputStream(file)
        reader.setInput(inputStream)

        val numberOfImages = reader.getNumImages(true)
        val imgPath = "tmp/DICOMFiles"

        for(i <- 0 until numberOfImages) {
          val bufferedImg: BufferedImage = reader.read(i, param)
          val outputFile = s"$imgPath/$i.jpg"

          val op = new IMOperation()
          op.addImage()
          op.resize(588, 452, "!")
          op.addImage()

          val convert = new ConvertCmd()
          convert.run(op, bufferedImg, outputFile)
        }

        val montageOp = new IMOperation()
        montageOp.addImage(s"$imgPath/*.jpg")
        montageOp.geometry(588, 452, 0, 0).border(0).background("none").mode("Concatenate").tile("20x")
        montageOp.addImage(s"$imgPath/$patientId.jpg")
        val montage = new MontageCmd()
        montage.run(montageOp)

        val qualityOp = new IMOperation()
        qualityOp.addImage(s"$imgPath/$patientId.jpg")
        qualityOp.strip.interlace("Plane").gaussianBlur(0.05).quality(0.75)
        qualityOp.addImage(s"$imgPath/$patientId.jpg")

        val convert = new ConvertCmd()
        convert.run(qualityOp)

        inputStream.close()
      })
    }
  }
}


