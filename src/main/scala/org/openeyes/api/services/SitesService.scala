package org.openeyes.api.services

import org.openeyes.api.data.SitesData

/**
 * Created by stu on 02/09/2014.
 */
object SitesService {

  def find(id: String) = {
    SitesData.sites.find(s => s.id.toString == id) match {
      case Some(site) => Some(site)
      case None => None
    }
  }

  def findAll = {
    SitesData.sites
  }
}
