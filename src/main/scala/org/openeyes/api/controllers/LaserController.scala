package org.openeyes.api.controllers

import org.json4s.mongo.ObjectIdSerializer
import org.json4s.{DefaultFormats, Formats}
import org.openeyes.api.models.{ApiError, Laser}
import org.openeyes.api.services.LaserService
import org.openeyes.api.stacks.ApiStack
import org.scalatra.swagger.{DataType, ParamType, Parameter, Swagger}
import org.scalatra.{NotFound, Ok}

/**
 * Created by stu on 03/09/2014.
 */
class LaserController(implicit val swagger: Swagger) extends ApiStack {

  protected val applicationDescription = "The Laser API"

  protected implicit val jsonFormats: Formats = DefaultFormats + new ObjectIdSerializer

  before() {
    contentType = formats("json")
  }

  val list = (apiOperation[List[Laser]]("listLasers")
    notes "Lists all known Lasers"
    parameters (
      queryParam[String]("siteId").description("An optional Site ID to filter the Lasers by").optional
    )
    summary "List Lasers"
    )

  get("/", operation(list)) {
    params.get("siteId") match {
      case Some(siteId) => LaserService.findAllForSite(siteId)
      case None => LaserService.findAll
    }
  }

  val get = (apiOperation[Laser]("getLaser")
    notes "Get a Laser by ID"
    parameters (
      pathParam[String]("id").description("The ID of the Laser to retrieve").required
    )
    summary "Get Laser"
    )

  get("/:id", operation(get)) {
    val id = params("id")
    LaserService.find(id) match {
      case Some(laser) => Ok(laser)
      case None => NotFound(ApiError("error\": \"No laser found for id '" + id + "'."))
    }
  }

}
