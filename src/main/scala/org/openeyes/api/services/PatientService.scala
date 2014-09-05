package org.openeyes.api.services

import org.bson.types.ObjectId
import org.openeyes.api.models._

/**
 * Created by stu on 02/09/2014.
 */
object PatientService {

  def find(id: String): Option[Patient] = {
    Patient.findOneById(new ObjectId(id))
  }

  def findAll = {
    Patient.findAll().toSeq
  }

  def search(searchTerm: String) = {
    Patient.search(searchTerm)
  }

}
