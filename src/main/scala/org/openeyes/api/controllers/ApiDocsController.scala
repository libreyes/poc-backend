package org.openeyes.api.controllers

import openeyesApi.BuildInfo
import org.scalatra.ScalatraServlet
import org.scalatra.swagger.{ApiInfo, JacksonSwaggerBase, Swagger}

class OpenEyesSwagger extends Swagger(Swagger.SpecVersion, BuildInfo.version, OpenEyesApiInfo)

object OpenEyesApiInfo extends ApiInfo(
  "The Openeyes API",
  "Docs for the Openeyes REST API",
  "http://openeyes.org",
  "apiteam@openeyes.org",
  "MIT",
  "http://opensource.org/licenses/MIT")

class ApiDocsController(implicit val swagger: Swagger) extends ScalatraServlet with JacksonSwaggerBase
