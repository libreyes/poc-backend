package org.openeyes.api.controllers

import org.scalatra.ScalatraServlet
import org.scalatra.swagger.{ApiInfo, JacksonSwaggerBase, Swagger}

object OpenEyesApiInfo extends ApiInfo(
  "The Openeyes API",
  "Docs for the Openeyes REST API",
  "http://openeyes.org",
  "apiteam@openeyes.org",
  "MIT",
  "http://opensource.org/licenses/MIT")


class ResourcesApp(implicit val swagger: Swagger) extends ScalatraServlet with JacksonSwaggerBase

class OpeneyesSwagger extends Swagger(Swagger.SpecVersion, "0.1.0", OpenEyesApiInfo)