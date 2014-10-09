package org.openeyes.api.services

import org.bson.types.ObjectId
import org.openeyes.api.commands.generateSampleEncounters
import org.openeyes.api.models.{Encounter,EncounterDao}
import org.openeyes.api.utils.Date._
import org.mockito.Mockito._
import org.scalacheck.Gen
import org.scalacheck.{Arbitrary,Properties}
import org.scalacheck.Prop.{forAll, BooleanOperators}
import org.springframework.context.annotation.AnnotationConfigApplicationContext

object ElementServiceSpec extends Properties("EncounterService") {
  val appContext = new AnnotationConfigApplicationContext("org.openeyes.api")

  val generator = appContext.getBean(classOf[generateSampleEncounters])

  val elementTypes = Seq("Allergies", "Anaesthetic", "AnteriorSegment", "ClinicalManagement", "ClinicOutcome", "Comorbidities", "Complications", "Dilation")

  implicit lazy val arbEncounter = Arbitrary {
    Encounter(
      new ObjectId, new ObjectId, setTimestamp,
      (0 to (generator.rand.nextInt(10) + 1) map (_ => generator.generateElement(elementTypes(generator.rand.nextInt(elementTypes.length)))) toList),
      None, None
    )
  }

  val mockEncounterDao = mock(classOf[EncounterDao])
  val elementService = new ElementService(mockEncounterDao)

  property("findAllForPatient_element_count_matches") = forAll { (encounters: Seq[Encounter]) => (encounters.length > 0) ==> {
    val patientId = new ObjectId

    when(mockEncounterDao.findAllForPatient(patientId.toString)).thenReturn(encounters)

    val elements = elementService.findAllForPatient(patientId.toString, None, None)

    elements.length == (encounters map (_.elements.length) reduceLeft (_ + _))
  }}
}
