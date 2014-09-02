package org.openeyes.api.models

import java.util.Date

import com.mongodb.casbah.MongoConnection
import com.novus.salat.annotations.raw.Key
import com.novus.salat.dao.{ModelCompanion, SalatDAO}
import com.novus.salat.global._
import org.bson.types.ObjectId


/**
 * Created by dave on 19/08/14.
 */
case class LaserEvent(@Key("_id")
                       _id: ObjectId,
                       leftEye: TreatedEye,
                       rightEye: TreatedEye,
                       laser: Laser,
                       site: Site)

case class Address(addressLine1: String, addressLine2: String, city: String, county: String, postcode: String)
case class AnteriorSegment(data: String)
case class ContactDetail(email: String, telephone: String)
case class Episode(events: Option[List[LaserEvent]])
case class GeneralPractitioner(firstName: String, surname: String, contactDetail: ContactDetail, address: Address,
                               practice: Practice)
case class Laser(name: String)
// NOTE: Added id to the Patient class so we can fake its persistence on the front end.
case class Patient(id: Integer, firstName: String, surname: String, dob: Date, gender: String, ethnicity: String,
                   contactDetail: ContactDetail, address: Address, nhsNumber: Integer, nextOfKin: String,
                   generalPractitioner: GeneralPractitioner)
case class Practice(name: String, contactDetail: ContactDetail, address: Address)
case class Procedure(label: String, codeValue: String, systemId: String)
case class Site(name: String)
case class TreatedEye(procedures: List[Procedure], anteriorSegment: AnteriorSegment)


object LaserEvent extends ModelCompanion[LaserEvent, ObjectId] {

  val collection = MongoConnection()("openeyes")("laser-events")
  val dao = new SalatDAO[LaserEvent, ObjectId](collection = collection) {}

}
