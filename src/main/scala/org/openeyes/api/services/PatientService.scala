package org.openeyes.api.services

import org.openeyes.api.data.PatientsData
import org.openeyes.api.models._

/**
 * Created by stu on 02/09/2014.
 */
object PatientService {

  def find(id: String) = {
    PatientsData.patients.find(p => p.id.toString == id) match {
      case Some(patient) => Some(patient)
      case None => None
    }
  }

  def findAll = {
    PatientsData.patients
  }

  def search(searchTerm: String) = {
    PatientsData.patients.filter(p => filterPatients(p, searchTerm)).toList
  }

  // NOTE: This filter patients method is mirroring the current search on the dev site, which searches on:
  //       firstName + " " + surname == term
  //       surname + ", " + firstName == term
  //       nhsNumber.toString == term
  //
  protected def filterPatients(patient: Patient, searchTerm: String): Boolean = {
    val formattedName = (patient.firstName + " " + patient.surname).toLowerCase
    val formattedNameWithComma = (patient.surname + ", " + patient.firstName).toLowerCase
    val nhsNumberAsString = patient.nhsNumber.toString
    val searchTermLower = searchTerm.toLowerCase

    formattedName == searchTermLower || formattedNameWithComma == searchTermLower || nhsNumberAsString == searchTerm
  }
}
