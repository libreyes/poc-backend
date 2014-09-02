package org.openeyes.api.services

import org.openeyes.api.models.Site

/**
 * Created by stu on 02/09/2014.
 */
object SitesService {

  def find(id: String) = {
    sites.find(s => s.id.toString == id) match {
      case Some(site) => Some(site)
      case None => None
    }
  }

  def findAll = {
    sites
  }

  // Fake sites
  //
  val sites = List(
    Site(1, "City Road"),
    Site(2, "Earling"),
    Site(3, "Barking")
  )
}
