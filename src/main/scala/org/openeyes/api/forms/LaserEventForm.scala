package org.openeyes.api.forms

import org.openeyes.api.models.{Laser, Site, TreatedEye}

/**
 * Created by jamie on 03/09/2014.
 */
case class LaserEventForm(leftEye: TreatedEye, rightEye: TreatedEye, laser: Laser, site: Site)
