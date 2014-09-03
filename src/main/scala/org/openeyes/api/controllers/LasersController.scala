package org.openeyes.api.controllers

import org.json4s.mongo.ObjectIdSerializer
import org.json4s.{DefaultFormats, Formats}
import org.openeyes.api.forms.LaserFormSupport
import org.openeyes.api.services.LasersService
import org.openeyes.api.stacks.ApiStack
import org.scalatra.swagger.Swagger
import org.scalatra.{NotFound, Ok}

/**
 * Created by stu on 03/09/2014.
 */
class LasersController(implicit val swagger: Swagger) extends ApiStack {

  protected val applicationDescription = "The lasers API."

  protected implicit val jsonFormats: Formats = DefaultFormats + new ObjectIdSerializer

  before() {
    contentType = formats("json")
  }

  get("/", laserForm) { form: LaserForm =>
    if (form.siteId != null) {
      LasersService.search(form.siteId)
    } else {
      LasersService.findAll
    }
  }

  get("/:id") {
    val id = params("id")
    LasersService.find(id) match {
      case Some(laser) => Ok(laser)
      case None => NotFound("No laser found for id '" + id + "'.")
    }
  }
}
