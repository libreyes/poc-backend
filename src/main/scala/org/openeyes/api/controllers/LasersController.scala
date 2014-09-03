package org.openeyes.api.controllers

import org.json4s.mongo.ObjectIdSerializer
import org.json4s.{DefaultFormats, Formats}
import org.openeyes.api.services.LaserService
import org.openeyes.api.stacks.ApiStack
import org.scalatra.{NotFound, Ok}
import org.scalatra.swagger.Swagger

/**
 * Created by stu on 03/09/2014.
 */
class LasersController(implicit val swagger: Swagger) extends ApiStack {

  protected val applicationDescription = "The lasers API."

  protected implicit val jsonFormats: Formats = DefaultFormats + new ObjectIdSerializer

  before() {
    contentType = formats("json")
  }

  get("/") {
    params.get("siteId") match {
      case Some(siteId) => LaserService.search(siteId)
      case None => LaserService.findAll
    }
  }

  get("/:id") {
    val id = params("id")
    LaserService.find(id) match {
      case Some(laser) => Ok(laser)
      case None => NotFound("No laser found for id '" + id + "'.")
    }
  }

}
