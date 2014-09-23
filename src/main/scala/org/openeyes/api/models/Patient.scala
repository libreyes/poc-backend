package org.openeyes.api.models

import com.mongodb.casbah.MongoConnection
import com.mongodb.casbah.commons.MongoDBObject
import com.novus.salat.annotations.raw.Key
import com.novus.salat.dao.{SalatDAO, ModelCompanion}
import com.novus.salat.global._
import org.bson.types.ObjectId

/**
 * Created by stu on 23/09/2014.
 */
case class Address(addressLine1: Option[String], addressLine2: Option[String], city: Option[String],
                   county: Option[String], postcode: Option[String])

case class ContactDetail(email: Option[String], telephone: Option[String])

case class GeneralPractitioner(firstName: Option[String], surname: Option[String], contactDetail: ContactDetail,
                               address: Option[Address], practice: Option[Practice])

case class Patient(@Key("_id") _id: ObjectId, id: String, firstName: String, surname: String, dob: String,
                   gender: String, ethnicity: String, contactDetail: ContactDetail, address: Option[Address],
                   nhsNumber: Option[String], nextOfKin: Option[String], generalPractitioner: GeneralPractitioner,
                   hospitalNumber: String)

case class Practice(name: String, contactDetail: ContactDetail, address: Address)

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
