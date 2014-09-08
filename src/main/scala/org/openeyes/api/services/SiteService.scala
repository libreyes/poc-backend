package org.openeyes.api.services

import org.openeyes.api.Site
import org.openeyes.api.fakeData.Sites

/**
 * Created by stu on 02/09/2014.
 */
object SiteService {

  def find(id: String) = {
    Sites.all.find(s => s.id == id.toInt) match {
      case Some(site) => Some(site)
      case None => None
    }
  }

  def findAll = {
    Site.findAll
  }
}
