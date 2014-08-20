package org.openeyes.api.services

import org.bson.types.ObjectId
import org.openeyes.api.models.{Weight, Observation}

/**
 * Created by dave on 19/08/2014.
 */
object ObservationService {

  def listAll = {
    Observation.findAll().toList
  }

  def create(weight: Int) = {
    val _id = new ObjectId
    val wght = Weight(weight)
    val observation = Observation(_id, wght)
    Observation.save(observation)
  }

}
