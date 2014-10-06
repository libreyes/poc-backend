package org.openeyes.api.services

import org.openeyes.api.models.EncounterDao
import org.openeyes.api.utils.Date._
import org.openeyes.api.utils.String._

/**
 * Created by stu on 22/09/2014.
 */
trait ElementService {
  protected val encounterDao: EncounterDao

  def findAllForPatient(patientId: String, elementType: Option[String], timestamp: Option[Long]) = {

    val elements = encounterDao.findAllForPatient(patientId)
      .filter(en => (if (timestamp.isDefined) timestampToShortDate(en.createdAt) == timestampToShortDate(timestamp.get) else true))
      .flatMap(en => en.elements)
      .filter(el => (if (elementType.isDefined) getObjectClassName(el) == elementType.get else true))

    elements
  }

}
