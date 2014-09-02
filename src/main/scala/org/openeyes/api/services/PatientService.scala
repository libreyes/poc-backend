package org.openeyes.api.services

import java.text.SimpleDateFormat
import java.util.Date

import org.openeyes.api.models._

/**
 * Created by stu on 02/09/2014.
 */
object PatientService {

  def search(term: String) = {

  }

  def find(id: String) = {
    patients find (_.id.toString == id) match {
      case Some(patient) => Some(patient)
      case None => None
    }
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
      67890, "Unknown", generalPractitioner)
  )




}
