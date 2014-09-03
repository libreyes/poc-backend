package org.openeyes.api.controllers

import org.json4s.mongo.ObjectIdSerializer
import org.json4s.{DefaultFormats, Formats}
import org.openeyes.api.services.SitesService
import org.openeyes.api.stacks.ApiStack
import org.scalatra.swagger.Swagger
import org.scalatra.{NotFound, Ok}

/**
 * Created by stu on 02/09/2014.
 */
class SitesController(implicit val swagger: Swagger) extends ApiStack {

  protected val applicationDescription = "The sites API."

  protected implicit val jsonFormats: Formats = DefaultFormats + new ObjectIdSerializer

  before() {
    contentType = formats("json")
  }

  get("/") {
    SitesService.findAll
  }

  get("/:id") {
    val id = params("id")
    SitesService.find(id) match {
      case Some(site) => Ok(site)
      case None => NotFound("No site found for id '" + id + "'.")
    }
  }
}
