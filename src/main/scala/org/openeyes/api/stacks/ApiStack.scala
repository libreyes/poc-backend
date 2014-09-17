package org.openeyes.api.stacks

import org.openeyes.api.stacks.com.constructiveproof.remotable.stacks.LoggerStack
import org.scalatra.ScalatraServlet
import org.scalatra.i18n.I18nSupport
import org.scalatra.json.JacksonJsonSupport
import org.scalatra.swagger.SwaggerSupport

/**
 * Created by dave on 19/08/2014.
 */
trait ApiStack extends ScalatraServlet with JacksonJsonSupport with I18nSupport with SwaggerSupport with LoggerStack
