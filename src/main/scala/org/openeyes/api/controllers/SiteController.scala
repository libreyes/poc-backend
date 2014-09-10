package org.openeyes.api.controllers

import org.json4s.mongo.ObjectIdSerializer
import org.json4s.{DefaultFormats, Formats}
import org.openeyes.api.models.{ApiError, Site}
import org.openeyes.api.services.SiteService
import org.openeyes.api.stacks.ApiStack
import org.scalatra.swagger.{DataType, ParamType, Parameter, Swagger}
import org.scalatra.{FutureSupport, NotFound, Ok}
import scala.concurrent.duration._

import scala.concurrent.{Await, ExecutionContext, Future}

/**
 * Created by stu on 02/09/2014.
 */
class SiteController(implicit val swagger: Swagger) extends ApiStack with FutureSupport {

  protected val applicationDescription = "The Site API."

  protected implicit val jsonFormats: Formats = DefaultFormats + new ObjectIdSerializer

  protected implicit def executor = ExecutionContext.global

  before() {
    contentType = formats("json")
  }

  val list = (apiOperation[List[Site]]("listSites")
    notes "Lists all known Sites"
    summary "List Sites"
    )

  get("/", operation(list)) {
    SiteService.findAll
  }

  val get = (apiOperation[Site]("getSite")
    notes "Get a Site by ID"
    parameters (
    Parameter("id", DataType.String, Some("The ID of the Site to retrieve"), None, ParamType.Path, required = true)
    )
    summary "Get Site"
    )

  get("/:id", operation(get)) {
    val id = params("id")
    val future = SiteService.find(id)
    val result = Await.result(future, 1 second) // yes, this is gross.
    result match {
      case Some(site) => Ok(site)
      case None => NotFound(ApiError("No site found for id '" + id + "'."))
    }
  }

}
