package org.openeyes.api.forms

import jp.sf.amateras.scalatra.forms._
import org.openeyes.api.stacks.com.constructiveproof.remotable.stacks.LoggerStack
import org.scalatra.i18n.I18nSupport
import org.scalatra.json.JacksonJsonSupport
import org.scalatra.servlet.ServletBase

/**
 * Created by stu on 03/09/2014.
 */
trait LaserFormSupport extends FormSupport with LoggerStack {
  self: ServletBase with I18nSupport with JacksonJsonSupport =>

  case class LaserForm(siteId: String)

  val laserForm = mapping(
    "siteId" -> text()
  )(LaserForm.apply)

}
