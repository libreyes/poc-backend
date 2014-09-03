package org.openeyes.api.controllers

import org.json4s.mongo.ObjectIdSerializer
import org.json4s.{DefaultFormats, Formats}
import org.openeyes.api.models.LaserOperator
import org.openeyes.api.services.LaserOperatorService
import org.openeyes.api.stacks.ApiStack
import org.scalatra.swagger.{ParamType, DataType, Parameter, Swagger}
import org.scalatra.{NotFound, Ok}

/**
 * Created by stu on 02/09/2014.
 */
class LaserOperatorsController(implicit val swagger: Swagger) extends ApiStack {

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
    parameters(
      Parameter("id", DataType.String, Some("The ID of the LaserOperator to retrieve"), None, ParamType.Path, required = true)
    )
    summary "Get LaserOperator"
  )

  get("/:id", operation(get)) {
    val id = params("id")
    LaserOperatorService.find(id) match {
      case Some(laserOperator) => Ok(laserOperator)
      case None => NotFound("{ \"error\": \"No laser operator found for id '" + id + "'.\" }")
    }
  }
}
