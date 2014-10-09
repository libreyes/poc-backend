package org.openeyes.api.services

import org.bson.types.ObjectId
import org.openeyes.api.utils.Date._
import org.openeyes.api.cqrs._
import org.openeyes.api.forms.EncounterForm
import org.openeyes.api.models.Encounter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Created by stu on 02/09/2014.
 */
@Service
class EncounterService @Autowired() (commandDispatcher: CommandDispatcher, readDispatcher: ReadDispatcher, finder: finders.Encounter) {

  def create(form: EncounterForm) = {
    val encounter = Encounter(
      new ObjectId,
      new ObjectId(form.patientId),
      setTimestamp,
      form.elements,
      form.ticketId map (new ObjectId(_)),
      form.stepIndex
    )

    commandDispatcher.dispatch(commands.CreateEncounter(encounter))

    encounter
  }

  def find(id: String) = {
    readDispatcher.dispatch(ReadRequest[Encounter, ObjectId](new ObjectId(id)))
  }

  def findAll() = {
    finder.find(new Query)
  }

  def findAllForPatient(patientId: String): Seq[Encounter] = {
    finder.find(new Query(Map(patientId -> new ObjectId(patientId))))
  }
}
