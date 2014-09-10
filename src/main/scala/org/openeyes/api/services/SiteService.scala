package org.openeyes.api.services

import org.openeyes.api.persistence.Sites

/**
 * Created by stu on 02/09/2014.
 */
object SiteService {

  def find(id: String) = {
    Sites.all.find(s => s.id == id) match {
      case Some(site) => Some(site)
      case None => None
    }
  }

  def findAll = {
    Sites.all
  }
}
