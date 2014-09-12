package org.openeyes.api.models

import com.mongodb.casbah.MongoConnection
import com.mongodb.casbah.commons.MongoDBObject
import com.novus.salat.annotations.raw.Key
import com.novus.salat.dao.{ModelCompanion, SalatDAO}
import com.novus.salat.global._
import org.bson.types.ObjectId
import scalikejdbc._
import skinny.orm._


/**
 * Created by dave on 19/08/14.
 */
case class Address(addressLine1: Option[String], addressLine2: Option[String], city: Option[String],
                   county: Option[String], postcode: Option[String])

case class ApiError(message: String)

case class AnteriorSegment(data: String)

case class ContactDetail(email: Option[String], telephone: Option[String])

case class Episode(events: Option[List[LaserEvent]])

case class GeneralPractitioner(firstName: Option[String], surname: Option[String], contactDetail: ContactDetail,
                               address: Option[Address], practice: Option[Practice])

case class Laser(id: Long, codeValue: String, label: String, systemId: String)
object Laser extends SkinnyMapper[Laser] {
  def defaultAlias = createAlias("l")
  def extract(rs: WrappedResultSet, rn: ResultName[Laser]) = autoConstruct(rs, rn)

//  lazy val laserSitesRef = hasMany[SiteLaser](
//    many = SiteLaser -> SiteLaser.,
//    merge = (laser, sites) => laser.copy(sites = sites)
//  )
//
//  def findAllBySiteId(siteId: Int)(implicit s: DBSession = autoSession): Seq[Laser] = {
//    findAllBy(sqls.eq(l.laserSites.siteId, siteId))
//  }
}

case class LaserEvent(@Key("_id") _id: ObjectId, patientId: String, leftEye: TreatedEye, rightEye: TreatedEye,
                      laser: Laser, site: Site, laserOperator: LaserOperator, createdAt: Long)

object LaserEvent extends ModelCompanion[LaserEvent, ObjectId] {

  val collection = MongoConnection()("openeyes")("laser-events")
  val dao = new SalatDAO[LaserEvent, ObjectId](collection = collection) {}

  def findAllForPatient(patientId: String): Seq[LaserEvent] = {
    find(
      MongoDBObject("patientId" -> patientId)
    ).toSeq
  }
}

// NOTE: Added id to the LaserOperator class so we can fake its persistence on the front end.
case class LaserOperator(id: String, firstName: String, surname: String)

// NOTE: Added id to the Patient class so we can fake its persistence on the front end.
case class Patient(@Key("_id") _id: ObjectId, id: String, firstName: String, surname: String, dob: String,
                   gender: String, ethnicity: String, contactDetail: ContactDetail, address: Option[Address],
                   nhsNumber: Option[String], nextOfKin: Option[String], generalPractitioner: GeneralPractitioner,
                   hospitalNumber: String)
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

case class Practice(name: String, contactDetail: ContactDetail, address: Address)

// NOTE: Added id to the Procedure class so we can fake its persistence on the front end.
case class Procedure(id: String, codeValue: String, label: String, systemId: String)

case class Site(id: Int, shortName: String, name: String, remoteId: String)
object Site extends SkinnyMapper[Site] {
  def defaultAlias = createAlias("s")
  def extract(rs: WrappedResultSet, rn: ResultName[Site]) = autoConstruct(rs, rn)
}

case class SiteLaser(siteId: Int, laserId: Int)
object SiteLaser extends SkinnyJoinTable[SiteLaser] {
  override lazy val tableName = "site_laser"
  override lazy val defaultAlias = createAlias("sl")
}

case class TreatedEye(procedures: List[Procedure], anteriorSegment: AnteriorSegment)

