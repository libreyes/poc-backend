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

  def create(form: LaserEventForm): LaserEvent = {
    val _id = new ObjectId
    val site = Site(form.site)
    var laser = Laser(form.laser)
    val leftEye = TreatedEye(form.leftEye.procedures, form.leftEye.anteriorSegment)
    val rightEye = TreatedEye(form.rightEye.procedures, form.rightEye.anteriorSegment)
    val laserEvent = LaserEvent(_id, leftEye, rightEye, laser, site)
    LaserEvent.save(laserEvent)
    laserEvent
  }

}
