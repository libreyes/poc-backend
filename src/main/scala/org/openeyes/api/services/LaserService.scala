package org.openeyes.api.services

import org.openeyes.api.fakeData.Lasers
import org.openeyes.api.models.Laser

/**
 * Created by stu on 03/09/2014.
 */
object LaserService {
  def find(id: Int) = {
    Laser.find(id)
  }

  def list = {
    Laser.list
  }

  def findAllForSite(siteId: String) = {
    Lasers.forSite(siteId)
  }
}
