package org.openeyes.api.services

import org.bson.types.ObjectId
import org.openeyes.api.forms.LaserEventForm
import org.openeyes.api.models._

/**
 * Created by dave on 19/08/2014.
 */
object LaserEventService {

  def findAll: Seq[LaserEvent] = {
    LaserEvent.findAll().toSeq
  }

  def find(id: String): Option[LaserEvent] = {
    LaserEvent.findOneById(new ObjectId(id))
  }

  def create(resource: LaserEventForm): LaserEvent = {
    val _id = new ObjectId
    val site = Site(resource.site.id, resource.site.codeValue, resource.site.label, resource.site.systemId)
    val laser = Laser(resource.laser.id, resource.laser.codeValue, resource.laser.label, resource.laser.systemId)
    val leftEye = TreatedEye(resource.leftEye.procedures, resource.leftEye.anteriorSegment)
    val rightEye = TreatedEye(resource.rightEye.procedures, resource.rightEye.anteriorSegment)
    val laserOperator = LaserOperator(resource.laserOperator.id, resource.laserOperator.firstName, resource.laserOperator.surname)
    val laserEvent = LaserEvent(_id, resource.patientId, leftEye, rightEye, laser, site, laserOperator)
    LaserEvent.save(laserEvent)
    laserEvent
  }

  def update(id: String, resource: LaserEventForm): LaserEvent = {
    val laserEvent = LaserEvent(new ObjectId(id), resource.patientId, resource.leftEye, resource.rightEye, resource.laser, resource.site, resource.laserOperator)
    LaserEvent.save(laserEvent)
    laserEvent
  }

}
