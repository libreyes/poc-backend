package org.openeyes.api.forms

import jp.sf.amateras.scalatra.forms._
import org.openeyes.api.models.{Procedure, AnteriorSegment, TreatedEye}

/**
 * Created by stu on 02/09/2014.
 */
object FormValidators {

  case class LaserEventForm(site: String, laser: String, leftEye: TreatedEye, rightEye: TreatedEye)
  case class PatientForm(term: String)

  val anteriorSegmentForm = mapping(
    "data" -> text(required)
  )(AnteriorSegment.apply)

  val procedureForm = mapping(
    "system" -> text(required),
    "code" -> text(required),
    "label" -> text(required)
  )(Procedure.apply)

  val treatedEyeForm = mapping(
    "procedures" -> list(
      FormValidators.procedureForm
    ),
    "anteriorSegment" -> FormValidators.anteriorSegmentForm
  )(TreatedEye.apply)
}
