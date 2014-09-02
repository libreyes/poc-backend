package org.openeyes.api.forms

import jp.sf.amateras.scalatra.forms._
import org.openeyes.api.forms.FormValidators.PatientForm
import org.openeyes.api.stacks.com.constructiveproof.remotable.stacks.LoggerStack
import org.scalatra.i18n.I18nSupport
import org.scalatra.json.JacksonJsonSupport
import org.scalatra.servlet.ServletBase

/**
 * Created by stu on 02/09/2014.
 */
trait PatientFormSupport extends FormSupport with LoggerStack {
  self: ServletBase with I18nSupport with JacksonJsonSupport =>

  val patientForm = mapping(
    "term" -> text(required)
  )(PatientForm.apply)

}
