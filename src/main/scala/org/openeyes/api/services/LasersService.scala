package org.openeyes.api.services

import org.openeyes.api.fakeData.Lasers

/**
 * Created by stu on 03/09/2014.
 */
object LasersService {
  def find(id: String) = {
    Lasers.all.find(s => s.id.toString == id) match {
      case Some(laser) => Some(laser)
      case None => None
    }
  }

  def findAll = {
    Lasers.all
  }

  def search(siteId: String) = {
    Lasers.all.filter(l => filterLasers(l, siteId)).toList
  }
}
