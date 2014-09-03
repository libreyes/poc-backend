package org.openeyes.api.forms

import jp.sf.amateras.scalatra.forms._
import org.openeyes.api.models.{TreatedEye, AnteriorSegment, Procedure}
import org.openeyes.api.stacks.com.constructiveproof.remotable.stacks.LoggerStack
import org.scalatra.i18n.I18nSupport
import org.scalatra.json.JacksonJsonSupport
import org.scalatra.servlet.ServletBase

case class TreatedEyeForm(procedures: List[Procedure], anteriorSegment: AnteriorSegment)

case class LaserEventForm(site: String, laser: String, leftEye: TreatedEyeForm, rightEye: TreatedEyeForm)

/**
 * Created by dave on 19/08/2014.
 */
trait LaserEventFormSupport extends ClientSideValidationFormSupport with LoggerStack {
  self: ServletBase with I18nSupport with JacksonJsonSupport =>

  val anteriorSegmentForm = mapping(
    "data" -> text(required)
  )(AnteriorSegment.apply)

  val procedureForm = mapping(
    "label" -> text(required),
    "code" -> text(required),
    "system" -> text(required)
  )(Procedure.apply)

  val treatedEyeFormMapping = mapping(
    "procedures" -> list[Procedure] (
      procedureForm
    ),
    "anteriorSegment" -> anteriorSegmentForm
  )(TreatedEyeForm.apply)

  val laserEventForm = mapping(
    "site" -> text(required),
    "laser" -> text(required),
    "leftEye" -> treatedEyeFormMapping,
    "rightEye" -> treatedEyeFormMapping
  )(LaserEventForm.apply)

}



