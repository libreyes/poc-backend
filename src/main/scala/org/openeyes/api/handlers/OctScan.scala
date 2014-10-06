package org.openeyes.api.handlers

import java.awt.image.BufferedImage
import java.io.{FileInputStream, File, IOException}
import java.util.ResourceBundle
import java.util.concurrent.Executors
import javax.imageio.ImageIO

import org.apache.commons.cli._
import org.apache.commons.io.FileUtils
import org.apache.commons.codec.binary.Base64

import org.dcm4che3.data.{Attributes, Tag, VR}
import org.dcm4che3.imageio.plugins.dcm.DicomImageReadParam
import org.dcm4che3.io.DicomInputStream.IncludeBulkData
import org.dcm4che3.io.{DicomInputStream, DicomOutputStream}
import org.dcm4che3.net.pdu.PresentationContext
import org.dcm4che3.net.service.{BasicCEchoSCP, BasicCStoreSCP, DicomServiceException, DicomServiceRegistry}
import org.dcm4che3.net.{ApplicationEntity, Association, Connection, Device, PDVInputStream, Status, TransferCapability}
import org.dcm4che3.tool.common.CLIUtils
import org.dcm4che3.util.{AttributesFormat, SafeClose}
import org.im4java.core.{MontageCmd, ConvertCmd, IMOperation}
import org.openeyes.api.di.ProductionEnvironment
import org.openeyes.api.forms.EncounterForm
import org.openeyes.api.models.{Image, OCTScan}
import org.slf4j.LoggerFactory

/**
 * Created by stu on 29/09/2014.
 */

class OctScan(recieveEvent: File => Unit) {

  private val conn = new Connection()
  private val ae = new ApplicationEntity("*")
  private val device = new Device("storescp")

  private val PART_EXT = ".part"

  private val LOG = LoggerFactory.getLogger(OctScan.getClass)

  private val cstoreSCP = new BasicCStoreSCP("*") {

    override protected def store(ass: Association, pc: PresentationContext, rq: Attributes, data: PDVInputStream, rsp: Attributes) {
      rsp.setInt(Tag.Status, VR.US, status)

      if (storageDir == null) return

      val cuid = rq.getString(Tag.AffectedSOPClassUID)
      val iuid = rq.getString(Tag.AffectedSOPInstanceUID)
      val tsuid = pc.getTransferSyntax
      val file = new File(storageDir, iuid + PART_EXT)

      try {
        storeTo(ass, ass.createFileMetaInformation(iuid, cuid, tsuid), data, file)

        val fpf = if(filePathFormat == null){ iuid
        } else filePathFormat.format(parse(file))

        val destFile = new File(storageDir, fpf)
        renameTo(ass, file, destFile)

        recieveEvent(destFile)
      } catch {
        case e: Exception => {
          LOG.debug("BASIC SCP Exception: " + e.getMessage)
          deleteFile(ass, file)
          throw new DicomServiceException(Status.ProcessingFailure, e)
        }
      }
    }
  }

  // TODO: In the Java code these are listed as private with no default value. Figure out if they do need to be private
  var status: Int = 0
  var storageDir: File = null
  var filePathFormat: AttributesFormat = null

  private def createServiceRegistry: DicomServiceRegistry = {
    val serviceRegistry = new DicomServiceRegistry()
    serviceRegistry.addDicomService(new BasicCEchoSCP())
    serviceRegistry.addDicomService(cstoreSCP)
    serviceRegistry
  }

  // TODO: This method is static in the Java class, should it be here?
  private def storeTo(ass: Association, fmi: Attributes, data: PDVInputStream, file: File) {
    LOG.info("{}: M-WRITE {}", Array(ass, file))

    file.getParentFile.mkdirs()
    val out = new DicomOutputStream(file)

    try {
      out.writeFileMetaInformation(fmi)
      data.copyTo(out)
    } finally {
      SafeClose.close(out)
    }
  }

  // TODO: This method is static in the Java class, should it be here?
  private def renameTo(ass: Association, from: File, dest: File) {
    LOG.info("{}: M-RENAME {}", Array(ass, from, dest))
    dest.getParentFile.mkdirs()

    if (!from.renameTo(dest)) throw new IOException("Failed to rename " + from + " to " + dest)
  }

  // TODO: This method is static in the Java class, should it be here?
  private def parse(file: File): Attributes = {
    val in = new DicomInputStream(file)

    try {
      in.setIncludeBulkData(IncludeBulkData.YES)
      in.readDataset(-1, Tag.PixelData)
    } finally {
      SafeClose.close(in)
    }
  }

  // TODO: This method is static in the Java class, should it be?
  private def deleteFile(ass: Association, file: File) {
    if (file.delete()){
      LOG.info("{}: M-DELETE {}", Array(ass, file))
    } else {
      LOG.warn("{}: M-DELETE {} failed!", Array(ass, file))
    }
  }

  def setStatus(status: Int) {
    this.status = status
  }

  def setStorageDirectory(storageDir: File) {
    if (storageDir != null) storageDir.mkdirs
    this.storageDir = storageDir
  }

  def setStorageFilePathFormat(pattern: String) {
    this.filePathFormat = new AttributesFormat(pattern)
  }

  // TODO: These are in an init block in the Java code, need to figure out how to do the same in Scala
  device.setDimseRQHandler(createServiceRegistry)
  device.addConnection(conn)
  device.addApplicationEntity(ae)
  ae.setAssociationAcceptor(true)
  ae.addConnection(conn)
}

object OctScan {
  private val env = ProductionEnvironment

  private val rb = ResourceBundle.getBundle("org.dcm4che3.tool.storescp.messages")

  // TODO: In the Java code these are listed as static but won't work with the code if put here. Figure out if they do need to be static
  private val PART_EXT = ".part"

  private def parseComandLine(args: Array[String]): CommandLine = {
    val opts = new Options()

    CLIUtils.addBindServerOption(opts)
    CLIUtils.addAEOptions(opts)
    CLIUtils.addCommonOptions(opts)

    addStatusOption(opts)
    addStorageDirectoryOptions(opts)
    addTransferCapabilityOptions(opts)

    CLIUtils.parseComandLine(args, opts, rb, OctScan.getClass);
  }

  private def addStatusOption(opts: Options) {
    opts.addOption({
      OptionBuilder.hasArg
      OptionBuilder.withArgName("code")
      OptionBuilder.withDescription(rb.getString("status"))
      OptionBuilder.withLongOpt("status")
      OptionBuilder.create(null)
    })
  }

  private def addStorageDirectoryOptions(opts: Options) {
    opts.addOption(null, "ignore", false, rb.getString("ignore"))

    opts.addOption({
      OptionBuilder.hasArg
      OptionBuilder.withArgName("path")
      OptionBuilder.withDescription(rb.getString("directory"))
      OptionBuilder.withLongOpt("directory")
      OptionBuilder.create(null)
    })

    opts.addOption({
      OptionBuilder.hasArg
      OptionBuilder.withArgName("pattern")
      OptionBuilder.withDescription(rb.getString("filepath"))
      OptionBuilder.withLongOpt("filepath")
      OptionBuilder.create(null)
    })
  }

  private def addTransferCapabilityOptions(opts: Options) {
    opts.addOption(null, "accept-unknown", false, rb.getString("accept-unknown"))

    opts.addOption({
      OptionBuilder.hasArg
      OptionBuilder.withArgName("file|url")
      OptionBuilder.withDescription(rb.getString("sop-classes"))
      OptionBuilder.withLongOpt("sop-classes")
      OptionBuilder.create(null)
    })
  }

  private def configureTransferCapability(ae: ApplicationEntity, cl: CommandLine) {
    ae.addTransferCapability(new TransferCapability(null, "*", TransferCapability.Role.SCP, "*"))
  }

  private def configureStorageDirectory(main: OctScan, cl: CommandLine) {
    if (!cl.hasOption("ignore")) {
      main.setStorageDirectory(new File(cl.getOptionValue("directory", ".")))
      if (cl.hasOption("filepath")) main.setStorageFilePathFormat(cl.getOptionValue("filepath"))
    }
  }

  def main(args: Array[String], storeEvent: File => Unit) {
    val main = new OctScan(storeEvent)

    try {
      val cl = parseComandLine(args)

      CLIUtils.configureBindServer(main.conn, main.ae, cl)
      CLIUtils.configure(main.conn, cl)

      main.setStatus(CLIUtils.getIntOption(cl, "status", 0))

      configureTransferCapability(main.ae, cl)
      configureStorageDirectory(main, cl)

      val executorService = Executors.newCachedThreadPool
      val scheduledExecutorService = Executors.newSingleThreadScheduledExecutor

      main.device.setScheduledExecutor(scheduledExecutorService)
      main.device.setExecutor(executorService)
      main.device.bindConnections()
    }catch{
      case pe: ParseException =>
        println("SCP ParseException: " + pe.getMessage)
        println(rb.getString("try"))
        main.device.unbindConnections()
//        System.exit(2)
      case e: Exception =>
        println("SCP Exception: " + e.getMessage)
        main.device.unbindConnections()
    }
  }

  def process(file: File) = {
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
    montageOp.geometry(546, 400, 0, 0).border(0).background("none").mode("Concatenate").tile("20x")
    montageOp.addImage(s"$imgPath/$patientId.jpg")
    val montage = new MontageCmd()
    montage.run(montageOp)

    val qualityOp = new IMOperation()
    qualityOp.addImage(s"$imgPath/$patientId.jpg")
    qualityOp.strip.interlace("Plane").gaussianBlur(0.05).quality(75.0)
    qualityOp.addImage(s"$imgPath/$patientId.jpg")

    val convert = new ConvertCmd()
    convert.run(qualityOp)

    inputStream.close()

    val octImg: Array[Byte] = FileUtils.readFileToByteArray(new File(s"$imgPath/$patientId.jpg"))
    val encodedImage = Base64.encodeBase64String(octImg)
    val image = Image(encodedImage, "image/jpeg")
    val element = OCTScan("right", image)
    val form = EncounterForm(patientId, List(element), None, None)
    env.encounterService.create(form)
  }
}
