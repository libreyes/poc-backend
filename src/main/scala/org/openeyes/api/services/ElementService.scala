package org.openeyes.api.services

import org.openeyes.api.models.{Encounter, Element}
import org.openeyes.api.utils.Date._
import org.openeyes.api.utils.String._

/**
 * Created by stu on 22/09/2014.
 */
object ElementService {

  def findAllForPatient(patientId: String, elementType: Option[String], timestamp: Option[Long]): Seq[Element] = {

    val elements = Encounter.findAllForPatient(patientId)
      .filter(en => (if (timestamp.isDefined) timestampToShortDate(en.createdAt) == timestampToShortDate(timestamp.get) else true))
      .flatMap(en => en.elements)
      .filter(el => (if (elementType.isDefined) getObjectClassName(el) == elementType.get else true))

    elements
  }

}
