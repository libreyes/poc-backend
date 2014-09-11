package org.openeyes.api.controllers

import org.json4s.mongo.ObjectIdSerializer
import org.json4s.{DefaultFormats, Formats}
import org.openeyes.api.models.{ApiError, LaserOperator}
import org.openeyes.api.services.LaserOperatorService
import org.openeyes.api.stacks.ApiStack
import org.scalatra.swagger.{DataType, ParamType, Parameter, Swagger}
import org.scalatra.{NotFound, Ok}

/**
 * Created by stu on 02/09/2014.
 */
class LaserOperatorController(implicit val swagger: Swagger) extends ApiStack {

  protected val applicationDescription = "The LaserOperator API"

  protected implicit val jsonFormats: Formats = DefaultFormats + new ObjectIdSerializer

  before() {
    contentType = formats("json")
  }

  val list = (apiOperation[List[LaserOperator]]("listLaserOperators")
    notes "Lists all known LaserOperators"
    summary "List LaserOperators"
    )

  get("/", operation(list)) {
    LaserOperatorService.findAll
  }

  val get = (apiOperation[LaserOperator]("getLaserOperator")
    notes "Get a LaserOperator by ID"
    parameters (
      pathParam[String]("id").description("The ID of the LaserOperator to retrieve").required
    )
    summary "Get LaserOperator"
    )

  get("/:id", operation(get)) {
    val id = params("id")
    LaserOperatorService.find(id) match {
      case Some(laserOperator) => Ok(laserOperator)
      case None => NotFound(ApiError("No laser operator found for id '" + id + "'."))
    }
  }
}
