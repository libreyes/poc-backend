package org.openeyes.api.services

import java.text.SimpleDateFormat
import org.openeyes.api.models._

/**
 * Created by stu on 02/09/2014.
 */
object PatientService {

  def find(id: String) = {
    patients.find(p => p.id.toString == id) match {
      case Some(patient) => Some(patient)
      case None => None
    }
  }

  def findAll = {
    patients
  }

  def search(searchTerm: String) = {
    patients.filter(p => filterPatients(p, searchTerm)).toList
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

  // Below is a whole load of fake data so that we can generate some patients to display in the POC.
  //
  val address = Address("1 Some Street", "", "London", "Greater London", "W1W 7JH")

  val contactDetail = ContactDetail("info@here.org", "+44 208 9734 6789")

  val practice = Practice("The Practice", contactDetail, address)

  val simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy")

  val generalPractitioner = GeneralPractitioner("Dr", "Strange", contactDetail, address, practice)

  val patients = List(
    Patient(1, "John", "Parnell", simpleDateFormat.parse("21/12/1954"), "Male", "Unknown", contactDetail, address,
      12345, "Unknown", generalPractitioner),
    Patient(2, "Steve", "Lonie", simpleDateFormat.parse("13/06/1969"), "Male", "Unknown", contactDetail, address,
      67890, "Unknown", generalPractitioner),
    Patient(3, "Victoria", "Markland", simpleDateFormat.parse("02/11/1977"), "Female", "Unknown", contactDetail, address,
      54321, "Unknown", generalPractitioner)
  )
}
