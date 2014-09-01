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
    val treatment1 = Treatment(procedures)
    val leftEye = TreatedEye()
    val laserEvent = LaserEvent(_id, leftEye, rightEye)
    LaserEvent.save(observation)
    observation
  }

}
