package org.openeyes.api.services

import java.text.SimpleDateFormat
import java.util.Date

import org.openeyes.api.models._

/**
 * Created by stu on 02/09/2014.
 */
object PatientService {

  def search(term: String) = {
    patients.find(p => filterPatients(p, term)) match {
      case Some(patient) => Some(patient)
      case None => None
    }
  }

  def find(id: String) = {
    patients.find(p => p.id.toString == id) match {
      case Some(patient) => Some(patient)
      case None => None
    }
  }

  // NOTE: this is in no way production ready code and can probably be done a lot better, like for instance what happens
  // if 2 people are found with the same name.
  protected def filterPatients(patient: Patient, term: String): Boolean = {
    val formattedName = patient.firstName + " " + patient.surname
    val formattedNameWithComma = patient.surname + ", " + patient.firstName

    formattedName.toLowerCase == term.toLowerCase || formattedNameWithComma.toLowerCase == term.toLowerCase || patient.nhsNumber.toString == term
  }

  // Below is a whole load of fake data so that we can generate some patients to display in the POC.

  val address = Address("Murray Hill", "", "Wimbledon", "Greater London", "SW4 7JH")

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
