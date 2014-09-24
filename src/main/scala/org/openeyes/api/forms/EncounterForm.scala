package org.openeyes.api.forms

import org.openeyes.api.models.Element

/**
 * Created by jamie on 15/09/2014.
 */
case class EncounterForm(patientId: String, elements: List[Element], ticketId: Option[String], stepIndex: Option[Int])
