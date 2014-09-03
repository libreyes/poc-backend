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
    val site = Site(resource.site.name)
    val laser = Laser(resource.laser.name)
    val leftEye = TreatedEye(resource.leftEye.procedures, resource.leftEye.anteriorSegment)
    val rightEye = TreatedEye(resource.rightEye.procedures, resource.rightEye.anteriorSegment)
    val laserEvent = LaserEvent(_id, leftEye, rightEye, laser, site)
    LaserEvent.save(laserEvent)
    laserEvent
  }

  def update(id: String, resource: LaserEventForm): LaserEvent = {
    val laserEvent = LaserEvent(new ObjectId(id), resource.leftEye, resource.rightEye, resource.laser, resource.site)
    LaserEvent.save(laserEvent)
    laserEvent
  }

}
