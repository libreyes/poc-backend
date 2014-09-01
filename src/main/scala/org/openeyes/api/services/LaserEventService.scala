package org.openeyes.api.services

import org.bson.types.ObjectId
import org.openeyes.api.models._

/**
 * Created by dave on 19/08/2014.
 */
object LaserEventService {

  def listAll: Seq[LaserEvent] = {
    LaserEvent.findAll().toSeq
  }

  def create(): LaserEvent = {
    val _id = new ObjectId
    val procedures = List(Procedure("Eyeball washing"), Procedure("Cycloablation"), Procedure("Macular Grid"))
    val as1 = AnteriorSegment("foo: 1")
    val site = Site("City Road")
    var laser = Laser("Moonraker")
    val leftEye = TreatedEye(Some(procedures), Some(as1))
    val rightEye = TreatedEye(Some(procedures), Some(as1))
    val laserEvent = LaserEvent(_id, Some(leftEye), Some(rightEye), laser, site)
    LaserEvent.save(laserEvent)
    laserEvent
  }

}
