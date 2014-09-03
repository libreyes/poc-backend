package org.openeyes.api.controllers

import org.json4s.mongo.ObjectIdSerializer
import org.json4s.{DefaultFormats, Formats}
import org.openeyes.api.models.Site
import org.openeyes.api.services.SiteService
import org.openeyes.api.stacks.ApiStack
import org.scalatra.swagger.{ParamType, DataType, Parameter, Swagger}
import org.scalatra.{NotFound, Ok}

/**
 * Created by stu on 02/09/2014.
 */
class SitesController(implicit val swagger: Swagger) extends ApiStack {

  protected val applicationDescription = "The Site API."

  protected implicit val jsonFormats: Formats = DefaultFormats + new ObjectIdSerializer

  before() {
    contentType = formats("json")
  }

  val listSites = (apiOperation[List[Site]]("listSites")
    notes "Lists all known Sites"
    summary "List Sites"
  )

  get("/", operation(listSites)) {
    SiteService.findAll
  }

  val getSite = (apiOperation[Site]("getSite")
    notes "Get a Site by ID"
    parameters(
      Parameter("id", DataType.String, Some("The ID of the Site to retrieve"), None, ParamType.Path, required = true)
    )
    summary "Get Site"
    )

  get("/:id", operation(getSite)) {
    val id = params("id")
    SiteService.find(id) match {
      case Some(site) => Ok(site)
      case None => NotFound("{ \"error\": \"No site found for id '" + id + "'.\" }")
    }
  }
}
