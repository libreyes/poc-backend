package org.openeyes.api.models

import com.novus.salat.annotations._

/**
 * Created by jamie on 17/09/2014.
 */
@Salat
trait Element

/**
 * Allergies
 * @param allergies
 */
case class Allergies(allergies: List[Allergy]) extends Element

case class Allergy(name: String, comment: Option[String])

/**
 * Anterior Segment
 * @param leftEye
 * @param rightEye
 */
case class AnteriorSegment(leftEye: Option[AnteriorSegmentSide], rightEye: Option[AnteriorSegmentSide]) extends Element

case class AnteriorSegmentSide(data: String)

/**
 * Comorbidities
 * @param comorbidities
 */
case class Comorbidities(comorbidities: List[String]) extends Element

/**
 * History
 * @param text
 */
case class History(text: String) extends Element

/**
 * Injection Management
 * @param leftEye
 * @param rightEye
 */
case class InjectionManagement(leftEye: Option[InjectionManagementSide], rightEye: Option[InjectionManagementSide]) extends Element

case class InjectionManagementSide(treatment: Boolean, diagnosis: String, diagnosisSecondaryTo: String, intendedTreatment: String, risks: List[String], comment: Option[String])

/**
 * Posterior Pole
 * @param leftEye
 * @param rightEye
 */
case class PosteriorPole(leftEye: Option[AnteriorSegmentSide], rightEye: Option[AnteriorSegmentSide]) extends Element

case class PosteriorPoleSide(data: String)

/**
 * Visual Acutity
 * @param leftEye
 * @param rightEye
 */
// TODO: Need to enforce at least one side
case class VisualAcuity(leftEye: Option[VisualAcuitySide], rightEye: Option[VisualAcuitySide]) extends Element

case class VisualAcuitySide(readings: List[VisualAcuityReading], comment: Option[String])

case class VisualAcuityReading(value: Int, correction: String)

