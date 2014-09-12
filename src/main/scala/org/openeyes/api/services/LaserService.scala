package org.openeyes.api.services

import org.openeyes.api.models.Laser

/**
 * Created by stu on 03/09/2014.
 */
object LaserService {
  def find(id: Int) = {
    Laser.findById(id)
  }

  def findAll = {
    Laser.findAll()
  }

  def findAllForSite(siteId: String) = {
//    Lasers.forSite(siteId)
  }
}
