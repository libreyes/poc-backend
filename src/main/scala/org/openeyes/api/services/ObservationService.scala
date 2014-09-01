package org.openeyes.api.services

import org.bson.types.ObjectId
import org.openeyes.api.models.{LaserEvent, Weight}

/**
 * Created by dave on 19/08/2014.
 */
object ObservationService {

  def listAll: Seq[LaserEvent] = {
    LaserEvent.findAll().toSeq
  }

  def create(weight: Int): LaserEvent = {
    val _id = new ObjectId
    val wght = Weight(weight)
    val observation = LaserEvent(_id, wght)
    LaserEvent.save(observation)
    observation
  }

}
