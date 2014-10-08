package org.openeyes.api.services

import org.bson.types.ObjectId
import org.scalamock.scalatest.MockFactory
import org.scalatest.FlatSpec
import org.openeyes.api.forms.EncounterForm
import org.openeyes.api.models.EncounterDao

class EncounterServiceSpec extends FlatSpec with MockFactory {
  private class Fixture {
    val mockTicketService = mock[workflow.TicketService]
    val mockEncounterDao = stub[EncounterDao]

    object TestEncounterService extends EncounterService {
      protected val ticketService = mockTicketService
      protected val encounterDao = mockEncounterDao
    }
  }

  "The Encounter Service" should "update the ticket step when creating an encounter associated with a ticket" in new Fixture {
    val patientId = new ObjectId
    val ticketId = new ObjectId

    (mockTicketService.updateStepIndexOrComplete _).expects(ticketId, 42)

    TestEncounterService.create(EncounterForm(patientId.toString, Nil, Some(ticketId.toString), Some(42)))
  }
}
