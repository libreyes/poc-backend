package org.openeyes.api.controllers.workflow

import org.json4s.mongo.ObjectIdSerializer
import org.json4s.{DefaultFormats, Formats}
import org.openeyes.api.stacks.ApiStack
import org.scalatra.swagger.Swagger

/**
 * Created by dave on 17/09/2014.
 */
class WorkflowQueueController(implicit val swagger: Swagger) extends ApiStack {

  protected val applicationDescription = "The Workflow Queue API"

  protected implicit val jsonFormats: Formats = DefaultFormats + new ObjectIdSerializer

  before() {
    contentType = formats("json")
  }

  val get = (apiOperation[])

  get("/:id") {

  }
}
