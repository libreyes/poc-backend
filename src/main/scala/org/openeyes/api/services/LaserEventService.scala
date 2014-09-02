package org.openeyes.api.services

import org.bson.types.ObjectId
import org.openeyes.api.models._

/**
 * Created by dave on 19/08/2014.
 */
object LaserEventService {

  def findAll: Seq[LaserEvent] = {
    LaserEvent.findAll().toSeq
  }

  def create(): LaserEvent = {
    val _id = new ObjectId
    val procedures = List(
      Procedure("Eyeball washing", "CODE", "SYSTEMID"),
      Procedure("Cycloablation", "CODE", "SYSTEMID"),
      Procedure("Macular Grid", "CODE", "SYSTEMID"))
    val as1 = AnteriorSegment("foo: 1")
    val site = Site(1, "City Road")
    var laser = Laser("Moonraker")
    val leftEye = TreatedEye(procedures, as1)
    val rightEye = TreatedEye(procedures, as1)
    val laserEvent = LaserEvent(_id, leftEye, rightEye, laser, site)
    LaserEvent.save(laserEvent)
    laserEvent
  }

}
