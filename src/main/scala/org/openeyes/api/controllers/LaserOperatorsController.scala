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

  protected val applicationDescription = "The Laser Operator API"

  protected implicit val jsonFormats: Formats = DefaultFormats + new ObjectIdSerializer

  before() {
    contentType = formats("json")
  }

  val listLaserOperators = (apiOperation[List[LaserOperator]]("listLaserOperators")
    notes "Lists all known Laser Operators"
    summary "List Laser Operators"
  )

  get("/", operation(listLaserOperators)) {
    LaserOperatorService.findAll
  }

  val getLaserOperator = (apiOperation[LaserOperator]("getLaserOperator")
    notes "Get a Laser Operator by ID"
    parameters(
      Parameter("id", DataType.String, Some("The ID of the Laser Operator to retrieve"), None, ParamType.Path, required = true)
    )
    summary "Get Laser Operator"
  )

  get("/:id", operation(getLaserOperator)) {
    val id = params("id")
    LaserOperatorService.find(id) match {
      case Some(laserOperator) => Ok(laserOperator)
      case None => NotFound("No laser found for id '" + id + "'.")
    }
  }
}
