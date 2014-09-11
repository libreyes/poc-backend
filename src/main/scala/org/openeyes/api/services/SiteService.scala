package org.openeyes.api.services

import org.openeyes.api.persistence.Sites

/**
 * Created by stu on 02/09/2014.
 */
object SiteService {

  def find(id: String) = {
    Sites.find(id)
  }

  def findAll = {
//    Sites.all
  }
}
