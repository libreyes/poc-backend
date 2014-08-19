package org.openeyes.api.stacks

import org.json4s.{DefaultFormats, Formats}
import org.scalatra.ScalatraServlet
import org.scalatra.i18n.I18nSupport
import org.scalatra.json.JacksonJsonSupport
import org.scalatra.swagger.{SwaggerSupport, JacksonSwaggerBase}

/**
 * Created by dave on 19/08/2014.
 */
trait ApiStack extends ScalatraServlet with JacksonJsonSupport with I18nSupport with SwaggerSupport {


}
