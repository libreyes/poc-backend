package org.openeyes.api.services

import org.openeyes.api.fakeData.Lasers

/**
 * Created by stu on 03/09/2014.
 */
object LaserService {
  def find(id: String) = {
    Lasers.all.find(s => s.id == id) match {
      case Some(laser) => Some(laser)
      case None => None
    }
  }

  def findAll = {
    Lasers.all
  }

  def findAllForSite(siteId: String) = {
    Lasers.forSite(siteId)
  }
}
