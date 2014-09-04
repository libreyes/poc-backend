package org.openeyes.api.services

import org.openeyes.api.fakeData.Patients
import org.openeyes.api.models._

/**
 * Created by stu on 02/09/2014.
 */
object PatientService {

  def find(id: String) = {
    Patients.all.find(p => p.id.toString == id) match {
      case Some(patient) => Some(patient)
      case None => None
    }
  }

  def findAll = {
    Patients.all
  }

  def search(searchTerm: String) = {
    Patients.search(searchTerm)
  }


}
