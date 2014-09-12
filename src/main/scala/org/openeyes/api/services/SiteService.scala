package org.openeyes.api.services

import org.openeyes.api.models.Site

/**
 * Created by stu on 02/09/2014.
 */
object SiteService {

  def find(id: Int) = {
    Site.findById(id)
  }

  def findAll = {
    Site.findAll()
  }

}
