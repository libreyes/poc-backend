package org.openeyes.api.services

import org.bson.types.ObjectId
import org.openeyes.api.models.{Observation, Weight}

/**
 * Created by dave on 19/08/2014.
 */
object ObservationService {

  def listAll: Seq[Observation] = {
    Observation.findAll().toSeq
  }

  def create(weight: Int): Observation = {
    val _id = new ObjectId
    val wght = Weight(weight)
    val observation = Observation(_id, wght)
    Observation.save(observation)
    observation
  }

}
