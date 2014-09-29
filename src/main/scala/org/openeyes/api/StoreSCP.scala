package org.openeyes.api

import java.io.File
import java.io.IOException
import java.util.Properties
import java.util.ResourceBundle
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService

import org.apache.commons.cli._
import org.dcm4che3.data.Tag
import org.dcm4che3.data.Attributes
import org.dcm4che3.data.VR
import org.dcm4che3.io.DicomInputStream
import org.dcm4che3.io.DicomOutputStream
import org.dcm4che3.io.DicomInputStream.IncludeBulkData
import org.dcm4che3.net.ApplicationEntity
import org.dcm4che3.net.Association
import org.dcm4che3.net.Connection
import org.dcm4che3.net.Device
import org.dcm4che3.net.PDVInputStream
import org.dcm4che3.net.Status
import org.dcm4che3.net.TransferCapability
import org.dcm4che3.net.pdu.PresentationContext
import org.dcm4che3.net.service.BasicCEchoSCP
import org.dcm4che3.net.service.BasicCStoreSCP
import org.dcm4che3.net.service.DicomServiceException
import org.dcm4che3.net.service.DicomServiceRegistry
import org.dcm4che3.tool.common.CLIUtils
import org.dcm4che3.util.AttributesFormat
import org.dcm4che3.util.SafeClose
import org.slf4j._

import scala.collection.JavaConversions._

/**
 * Created by stu on 29/09/2014.
 */
class StoreSCP {

  private val conn = new Connection()
  private val ae = new ApplicationEntity("*")
  private val device = new Device("storescp")

  private val PART_EXT = ".part"
  private val LOG = LoggerFactory.getLogger(StoreSCP.getClass)

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

        renameTo(ass, file, new File(storageDir, fpf))
      } catch {
        case e: Exception => {
          println("storescp: " + e.getMessage)
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

  // TODO: This is private in the Java code but have had to make public as we are calling it in the main method, probable should be made private again
  def createServiceRegistry: DicomServiceRegistry = {
    val serviceRegistry = new DicomServiceRegistry()
    serviceRegistry.addDicomService(new BasicCEchoSCP())
    serviceRegistry.addDicomService(cstoreSCP)
    serviceRegistry
  }

  // TODO: This method is static in the Java class, should it be?
  private def storeTo(ass: Association, fmi: Attributes, data: PDVInputStream, file: File) {
    // TODO: Log not worky
    // LOG.info("{}: M-WRITE {}", ass, file)

    file.getParentFile.mkdirs()
    val out = new DicomOutputStream(file)

    try {
      out.writeFileMetaInformation(fmi)
      data.copyTo(out)
    } finally {
      SafeClose.close(out)
    }
  }

  // TODO: This method is static in the Java class, should it be?
  private def renameTo(ass: Association, from: File, dest: File) {
    // TODO: Log not worky
    // LOG.info("{}: M-RENAME {}", new Object[]{ ass, from, dest })
    dest.getParentFile().mkdirs()

    if (!from.renameTo(dest)) throw new IOException("Failed to rename " + from + " to " + dest)
  }

  // TODO: This method is static in the Java class, should it be?
  private def parse(file: File): Attributes = {
    val in = new DicomInputStream(file)

    try {
      in.setIncludeBulkData(IncludeBulkData.NO)
      return in.readDataset(-1, Tag.PixelData)
    } finally {
      SafeClose.close(in)
    }
  }

  // TODO: This method is static in the Java class, should it be?
  private def deleteFile(ass: Association, file: File) {
    if (file.delete()){
      println("Deleting file: " + file)
      // TODO: Log not worky
      // LOG.info("{}: M-DELETE {}", ass, file)
    } else {
      println("Deleting file: " + file)
      // TODO: Log not worky
      // LOG.warn("{}: M-DELETE {} failed!", ass, file)
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
}

object StoreSCP {

  private val rb = ResourceBundle.getBundle("org.dcm4che3.tool.storescp.messages")

  // TODO: In the Java code these are listed as static but won't work with the code if put here. Figure out if they do need to be static
  private val PART_EXT = ".part"
  private val LOG = LoggerFactory.getLogger(StoreSCP.getClass)

  private def parseComandLine(args: Array[String]): CommandLine = {
    val opts = new Options()

    CLIUtils.addBindServerOption(opts)
    CLIUtils.addAEOptions(opts)
    CLIUtils.addCommonOptions(opts)

    addStatusOption(opts)
    addStorageDirectoryOptions(opts)
    addTransferCapabilityOptions(opts)

    CLIUtils.parseComandLine(args, opts, rb, StoreSCP.getClass);
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
    if (cl.hasOption("accept-unknown")) {
      ae.addTransferCapability(new TransferCapability(null, "*", TransferCapability.Role.SCP, "*"))
    } else {
      val p = CLIUtils.loadProperties(cl.getOptionValue("sop-classes", "resource:sop-classes.properties"), null)

      for (cuid <- p.stringPropertyNames()) {
        val ts = p.getProperty(cuid)
        val tc = new TransferCapability(null, CLIUtils.toUID(cuid), TransferCapability.Role.SCP, CLIUtils.toUIDs(ts): _*)
        ae.addTransferCapability(tc)
      }
    }
  }

  private def configureStorageDirectory(main: StoreSCP, cl: CommandLine) {
    if (!cl.hasOption("ignore")) {
      main.setStorageDirectory(new File(cl.getOptionValue("directory", ".")))
      if (cl.hasOption("filepath")) main.setStorageFilePathFormat(cl.getOptionValue("filepath"))
    }
  }

  def main(args: Array[String]) {
    try {
      val cl = parseComandLine(args)
      val main = new StoreSCP()

      // TODO: These are in an init block in the Java code, need to figure out how to do the same in Scala
      main.device.setDimseRQHandler(main.createServiceRegistry)
      main.device.addConnection(main.conn)
      main.device.addApplicationEntity(main.ae)
      main.ae.setAssociationAcceptor(true)
      main.ae.addConnection(main.conn)

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
      case pe: ParseException => {
        println("storescp: " + pe.getMessage)
        println(rb.getString("try"))
        System.exit(2)
      }
      case e: Exception => {
        println("storescp: " + e.getMessage)
        System.exit(2)
      }
    }
  }
}
