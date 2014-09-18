package org.openeyes.api.models

import com.mongodb.casbah.MongoConnection
import com.mongodb.casbah.commons.MongoDBObject
import com.novus.salat.annotations.raw.Key
import com.novus.salat.dao.{ModelCompanion, SalatDAO}
import com.novus.salat.global._
import org.bson.types.ObjectId
import org.openeyes.api.elements.Element


/**
 * Created by dave on 19/08/14.
 */

case class Address(addressLine1: Option[String], addressLine2: Option[String], city: Option[String],
                   county: Option[String], postcode: Option[String])

case class ApiError(message: String)

case class ContactDetail(email: Option[String], telephone: Option[String])

case class Encounter(@Key("_id") _id: ObjectId, patientId: ObjectId, createdAt: Long, elements: List[Element])

case class GeneralPractitioner(firstName: Option[String], surname: Option[String], contactDetail: ContactDetail,
                               address: Option[Address], practice: Option[Practice])

// NOTE: Added id to the Patient class so we can fake its persistence on the front end.
case class Patient(@Key("_id") _id: ObjectId, id: String, firstName: String, surname: String, dob: String,
                   gender: String, ethnicity: String, contactDetail: ContactDetail, address: Option[Address],
                   nhsNumber: Option[String], nextOfKin: Option[String], generalPractitioner: GeneralPractitioner,
                   hospitalNumber: String)

case class Practice(name: String, contactDetail: ContactDetail, address: Address)

// NOTE: Added id to the Procedure class so we can fake its persistence on the front end.
case class Procedure(id: String, codeValue: String, label: String, systemId: String)

// NOTE: Added id to the Site class so we can fake its persistence on the front end.
case class Site(id: String, codeValue: String, label: String, systemId: String)

object Encounter extends ModelCompanion[Encounter, ObjectId] {

  val collection = MongoConnection()("openeyes")("encounters")
  val dao = new SalatDAO[Encounter, ObjectId](collection = collection) {}

  def findAllForPatient(patientId: String): Seq[Encounter] = {
    find(
      MongoDBObject("patientId" -> new ObjectId(patientId))
    ).toSeq
  }
}

object Patient extends ModelCompanion[Patient, ObjectId] {

  val collection = MongoConnection()("openeyes")("patients")
  val dao = new SalatDAO[Patient, ObjectId](collection = collection) {}

  def search(searchTerm: String): Seq[Patient] = {
    val regex = MongoDBObject("$regex" -> searchTerm, "$options" -> "i")
    find(
      MongoDBObject("$or" -> List(
        MongoDBObject("nhsNumber" -> searchTerm),
        MongoDBObject("hospitalNumber" -> searchTerm),
        MongoDBObject("firstName" -> regex),
        MongoDBObject("surname" -> regex)
      ))
    ).toSeq
  }
}
