package org.openeyes.api.controllers

import ij.io.{FileSaver, Opener}
import org.json4s.mongo.ObjectIdSerializer
import org.json4s.{DefaultFormats, Formats}
import org.openeyes.api.stacks.ApiStack
import org.scalatra.swagger.Swagger
import ij._
import scala.io.Source
import de.iftm.dcm4che.DcmDataImage
import de.iftm.ij.plugins.dcmie.FileExporter

/**
 * Created by dave on 12/09/2014.
 */
class DicomController(implicit val swagger: Swagger) extends ApiStack {

  val applicationDescription = "Converts DICOM images to web formats"

  protected implicit val jsonFormats: Formats = DefaultFormats + new ObjectIdSerializer

  before() {
    contentType = formats("json")
  }

  get("/:id/:contenttype") {
    val file = "/tmp/IM-0001-0003.dcm"
    contentType = "application/octet-stream"
    val opener = new Opener
    val image = opener.openImage(file)
    val dicomimage = new DcmDataImage
    val foo = dicomimage.setImage(image)

    val saver = new FileSaver(image)
    saver.saveAsPng("/tmp/foo.png")
    response.setHeader("Content-Disposition", "attachment; filename=")
    file
  }

}
