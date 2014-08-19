package org.openeyes.api.stacks

import org.scalatra.ScalatraServlet
import org.scalatra.i18n.I18nSupport
import org.scalatra.json.JacksonJsonSupport

/**
 * Created by dave on 19/08/2014.
 */
trait ApiStack extends ScalatraServlet with JacksonJsonSupport with I18nSupport {

}
