package org.openeyes.api.services

import org.bson.types.ObjectId
import org.openeyes.api.Utilities._
import org.openeyes.api.forms.EncounterForm
import org.openeyes.api.models._

/**
 * Created by stu on 02/09/2014.
 */
object EncounterService {

  def create(form: EncounterForm): Encounter = {
    val encounter = Encounter(new ObjectId, new ObjectId(form.patientId), setTimestamp, setTimestamp, form.elements)
    Encounter.save(encounter)
    encounter
  }

  def find(id: String) = {
    Encounter.findOneById(new ObjectId(id))
  }

  def findAll = {
    Encounter.findAll().toSeq
  }

  def findAllForPatient(patientId: String): Seq[Encounter] = {
    Encounter.findAllForPatient(patientId)
  }

}
