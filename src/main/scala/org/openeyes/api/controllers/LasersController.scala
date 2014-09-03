package org.openeyes.api.controllers

import org.json4s.mongo.ObjectIdSerializer
import org.json4s.{DefaultFormats, Formats}
import org.openeyes.api.models.Laser
import org.openeyes.api.services.LaserService
import org.openeyes.api.stacks.ApiStack
import org.scalatra.swagger.{ParamType, DataType, Parameter, Swagger}
import org.scalatra.{NotFound, Ok}

/**
 * Created by stu on 03/09/2014.
 */
class LasersController(implicit val swagger: Swagger) extends ApiStack {

  protected val applicationDescription = "The Laser API"

  protected implicit val jsonFormats: Formats = DefaultFormats + new ObjectIdSerializer

  before() {
    contentType = formats("json")
  }

  val listLasers = (apiOperation[List[Laser]]("listLasers")
    notes "Lists all known Lasers"
    parameters(
      Parameter("siteId", DataType.String, Some("An optional Site ID to filter the Lasers by"), None, ParamType.Query, required = false)
    )
    summary "List Lasers"
  )

  get("/", operation(listLasers)) {
    params.get("siteId") match {
      case Some(siteId) => LaserService.search(siteId)
      case None => LaserService.findAll
    }
  }

  val getLaser = (apiOperation[Laser]("getLaser")
    notes "Get a Laser by ID"
    parameters(
      Parameter("id", DataType.String, Some("The ID of the Laser to retrieve"), None, ParamType.Path, required = true)
    )
    summary "Get Laser"
  )

  get("/:id", operation(getLaser)) {
    val id = params("id")
    LaserService.find(id) match {
      case Some(laser) => Ok(laser)
      case None => NotFound("No laser found for id '" + id + "'.")
    }
  }

}
