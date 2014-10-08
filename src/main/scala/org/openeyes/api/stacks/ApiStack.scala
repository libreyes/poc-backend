package org.openeyes.api.stacks

import org.json4s.mongo.ObjectIdSerializer
import org.json4s.{MappingException, DefaultFormats, Formats}
import org.openeyes.api.models.ApiError
import org.openeyes.api.stacks.com.constructiveproof.remotable.stacks.LoggerStack
import org.scalatra.ScalatraServlet
import org.scalatra.i18n.I18nSupport
import org.scalatra.json.JacksonJsonSupport
import org.scalatra.swagger.SwaggerSupport

/**
 * Created by dave on 19/08/2014.
 */
trait ApiStack extends ScalatraServlet with JacksonJsonSupport with I18nSupport with SwaggerSupport with LoggerStack {
  protected implicit val jsonFormats: Formats = DefaultFormats + new ObjectIdSerializer

  before() {
    contentType = formats("json")
  }

  error {
    case e: MappingException => halt(400, ApiError(e.getMessage))
    case e => halt(500, ApiError(e.getMessage))
  }

  notFound {
    halt(404, ApiError("Nothing found for resource"))
  }
}
