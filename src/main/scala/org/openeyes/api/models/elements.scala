package org.openeyes.api.models

import com.novus.salat.annotations._

@Salat
trait Element

/**
 * Allergies
 * @param allergies
 */
case class Allergies(allergies: List[Allergy]) extends Element

case class Allergy(name: String, comment: Option[String])

/**
 * Anaesthetic
 * @param leftEye
 * @param rightEye
 */
case class Anaesthetic (leftEye: Option[AnaestheticSide], rightEye: Option[AnaestheticSide]) extends Element

case class AnaestheticSide (anaestheticType: String, delivery: String, agent: String)

/**
 * Anterior Segment
 * @param leftEye
 * @param rightEye
 */
case class AnteriorSegment(leftEye: Option[AnteriorSegmentSide], rightEye: Option[AnteriorSegmentSide]) extends Element

case class AnteriorSegmentSide(data: String, comments: Option[String])

/**
 * Clinical Management
 * @param comments
 */
case class ClinicalManagement(comments: String) extends Element

/**
 * Clinic Outcome
 * @param status
 * @param quantity
 * @param period
 * @param communityPatient
 * @param role
 */
case class ClinicOutcome(status: String, quantity: Int, period: String, communityPatient: Boolean, role: String) extends Element

/**
 * Comorbidities
 * @param comorbidities
 */
case class Comorbidities(comorbidities: List[String]) extends Element

/**
 * Complications
 * @param leftEye
 * @param rightEye
 */
case class Complications (leftEye: Option[ComplicationsSide], rightEye: Option[ComplicationsSide]) extends Element

case class ComplicationsSide (complications: List[String])

/**
 * Dilation
 */
case class Dilation(leftEye: Option[DilationSide], rightEye: Option[DilationSide]) extends Element

case class DilationSide(dilations: List[DilationRecord])

case class DilationRecord(time: String, drug: String, drops: Int)

/**
 * History
 * @param text
 */
case class History(text: String) extends Element

/**
 * Injection Site
 * @param leftEye
 * @param rightEye
 */
case class InjectionSite (leftEye: Option[InjectionSiteSide], rightEye: Option[InjectionSiteSide]) extends Element

case class InjectionSiteSide (data: String, lensStatus: String)

/**
 * Injection Management
 * @param leftEye
 * @param rightEye
 */
case class InjectionManagement(leftEye: Option[InjectionManagementSide], rightEye: Option[InjectionManagementSide]) extends Element

case class InjectionManagementSide(treatment: Boolean, diagnosis: String, diagnosisSecondaryTo: String, questions: List[String],
                                   intendedTreatment: String, risks: List[String], comment: Option[String])

/**
 * Posterior Pole
 * @param leftEye
 * @param rightEye
 */
case class PosteriorPole(leftEye: Option[AnteriorSegmentSide], rightEye: Option[AnteriorSegmentSide]) extends Element

case class PosteriorPoleSide(data: String, comments: Option[String])

/**
 * Post Injection Exam
 * @param leftEye
 * @param rightEye
 */
case class PostInjectionExam(leftEye: Option[PostInjectionExamSide], rightEye: Option[PostInjectionExamSide]) extends Element

case class PostInjectionExamSide(countingFingers: Boolean, iop: Boolean, drops: String)

/**
 * Treatment
 * @param leftEye
 * @param rightEye
 */
case class Treatment(leftEye: Option[TreatmentSide], rightEye: Option[TreatmentSide]) extends Element

case class TreatmentSide(batchNumber: String, batchExpiryDate: String, injectionGivenBy: String)

/**
 * Treatment Order
 * @param leftEye
 * @param rightEye
 */
case class TreatmentOrder(leftEye: Option[PostInjectionExamSide], rightEye: Option[PostInjectionExamSide]) extends Element

case class TreatmentOrderSide(preAntiseptic: String, preSkinCleanser: String, preIopLoweringTherapy: List[String], drug: String,
                              sequenceNumber: Int, injectionTime: String, postIopLoweringTherapy: List[String])

/**
 * Visual Acutity
 * @param leftEye
 * @param rightEye
 */
case class VisualAcuity(leftEye: Option[VisualAcuitySide], rightEye: Option[VisualAcuitySide]) extends Element

case class VisualAcuitySide(readings: List[VisualAcuityReading], comment: Option[String])

case class VisualAcuityReading(value: Int, correction: String)

