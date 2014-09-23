package org.openeyes.api.services

import org.openeyes.api.models.{Encounter, Element}
import org.openeyes.api.Utilities.Date._
import org.openeyes.api.Utilities.String._

/**
 * Created by stu on 22/09/2014.
 */
object ElementService {

  def findAllForPatient(patientId: String, elementType: Option[String], timestamp: Option[Long]): Seq[Element] = {

    val encounters = timestamp match {
      case Some(timestamp) => Encounter.findAllForPatient(patientId)
        .filter(e => timestampToShortDate(e.createdAt) == timestampToShortDate(timestamp))
      case None => Encounter.findAllForPatient(patientId)
    }

    val elements = elementType match {
      case Some(elementType) => encounters.flatMap(e => e.elements)
        .filter(e => getObjectClassName(e) == elementType)
      case None => encounters.flatMap(e => e.elements)
    }

    elements
  }
}
