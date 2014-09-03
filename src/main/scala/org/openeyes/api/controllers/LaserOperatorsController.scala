package org.openeyes.api.controllers

import org.json4s.mongo.ObjectIdSerializer
import org.json4s.{DefaultFormats, Formats}
import org.openeyes.api.services.LaserOperatorService
import org.openeyes.api.stacks.ApiStack
import org.scalatra.swagger.Swagger

/**
 * Created by stu on 02/09/2014.
 */
class LaserOperatorsController(implicit val swagger: Swagger) extends ApiStack {

  protected val applicationDescription = "The sites API."

  protected implicit val jsonFormats: Formats = DefaultFormats + new ObjectIdSerializer

  before() {
    contentType = formats("json")
  }

  get("/") {
    LaserOperatorService.findAll
  }
}
