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

// NOTE: Added id to the Laser class so we can fake its persistence on the front end.
case class Laser(id: String, codeValue: String, label: String, systemId: String)

// NOTE: Added id to the LaserOperator class so we can fake its persistence on the front end.
case class LaserOperator(id: String, firstName: String, surname: String)

// NOTE: Added id to the Patient class so we can fake its persistence on the front end.
case class Patient(id: String, firstName: String, surname: String, dob: Date, gender: String, ethnicity: String,
                   contactDetail: ContactDetail, address: Address, nhsNumber: Int, nextOfKin: String,
                   generalPractitioner: GeneralPractitioner, hospitalNumber: Int)

case class Practice(name: String, contactDetail: ContactDetail, address: Address)

case class Procedure(label: String, codeValue: String, systemId: String)

// NOTE: Added id to the Site class so we can fake its persistence on the front end.
case class Site(id: String, codeValue: String, label: String, systemId: String)

case class TreatedEye(procedures: List[Procedure], anteriorSegment: AnteriorSegment)


object LaserEvent extends ModelCompanion[LaserEvent, ObjectId] {

  val collection = MongoConnection()("openeyes")("laser-events")
  val dao = new SalatDAO[LaserEvent, ObjectId](collection = collection) {}

}
