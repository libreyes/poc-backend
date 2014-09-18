package org.openeyes.api;


package object elements {
  import com.novus.salat.annotations._

  @Salat
  trait Element

  case class SidedElement[SideType](leftEye: Option[SideType], rightEye: Option[SideType]) extends Element

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
  type AnteriorSegment = SidedElement[AnteriorSegmentSide]

  case class AnteriorSegmentSide(data: String)

  /**
    * Comorbidities
    * @param comorbidities
    */
  case class Comorbidities(comorbidities: List[Comorbidity]) extends Element

  case class Comorbidity(name: String)

  /**
    * History
    * @param text
    */
  case class History(text: String) extends Element

  case class InjectionAnaesthetic (leftEye: Option[InjectionAnaestheticSide], rightEye: Option[InjectionAnaestheticSide]) extends Element

  case class InjectionAnaestheticSide (anaestheticType: String, delivery: String, agent: String)

  case class InjectionComplications (leftEye: Option[InjectionComplicationsSide], rightEye: Option[InjectionComplicationsSide]) extends Element

  case class InjectionComplicationsSide (complications: List[String])

  case class Injection (leftEye: Option[InjectionSide], rightEye: Option[InjectionSide]) extends Element

  case class InjectionSide (preAntiseptic: String, preSkinCleanser: String, preIopLoweringTherapy: Option[String], drug: String, sequenceNumber: Int, batchNumber: String, batchExpiryDate: String, givenByUserId: String, injectionTime: String, postIopLoweringTherapy: Option[String])

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

  case class PosteriorPoleSide(data: String)

  case class PostInjectionExam(leftEye: Option[PostInjectionExamSide], rightEye: Option[PostInjectionExamSide]) extends Element

  case class PostInjectionExamSide(countingFingers: Boolean, iop: Boolean, drops: String)

  /**
    * Visual Acutity
    * @param leftEye
    * @param rightEye
    */
  // TODO: Need to enforce at least one side
  case class VisualAcuity(leftEye: Option[VisualAcuitySide], rightEye: Option[VisualAcuitySide]) extends Element

  case class VisualAcuitySide(readings: List[VisualAcuityReading], comment: Option[String])

  case class VisualAcuityReading(value: Int, correction: String)
}

