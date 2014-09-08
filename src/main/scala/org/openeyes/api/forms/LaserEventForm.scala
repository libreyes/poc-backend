package org.openeyes.api.forms


import org.openeyes.api.models.{Laser, LaserOperator, TreatedEye}
import org.openeyes.api.Site

/**
 * Created by jamie on 03/09/2014.
 */
case class LaserEventForm(patientId: String, leftEye: TreatedEye, rightEye: TreatedEye, laser: Laser, site: Site, laserOperator: LaserOperator)
