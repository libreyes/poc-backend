package org.openeyes.api.services

import org.bson.types.ObjectId
import org.mockito.Mockito._
import org.openeyes.api.forms.EncounterForm
import org.openeyes.api.models.{Encounter, EncounterDao}
import org.scalatest.WordSpec
import org.scalatest.mock.MockitoSugar
import org.mockito.Matchers._

class EncounterServiceSpec extends WordSpec with MockitoSugar {

  "Create method" when {
    def fixture = new {
      val mockEncounterDao = mock[EncounterDao]
      val mockTicketService = mock[workflow.TicketService]
      val encounterService = TestEncounterService

      object TestEncounterService extends EncounterService {
        protected val encounterDao = mockEncounterDao
        protected val ticketService = mockTicketService
      }
    }

    "not associated with a ticket" should {
      "only save the Encounter" in {
        val f = fixture
        val patientId = new ObjectId
        val form = EncounterForm(patientId.toString, Nil, None, None)

        f.encounterService.create(form)

        verify(f.mockEncounterDao).save(any(Encounter.getClass).asInstanceOf[Encounter])
        verifyZeroInteractions(f.mockTicketService)
      }
    }

    "when associated to a ticket" should {
      "save the Encounter and update the step index when associated with a ticket" in {
        val f = fixture
        val patientId = new ObjectId
        val ticketId = new ObjectId
        val form = EncounterForm(patientId.toString, Nil, Some(ticketId.toString), Some(42))

        f.encounterService.create(form)

        verify(f.mockEncounterDao).save(any(Encounter.getClass).asInstanceOf[Encounter])
        verify(f.mockTicketService).updateStepIndexOrComplete(ticketId, 42)
      }
    }
  }
}
