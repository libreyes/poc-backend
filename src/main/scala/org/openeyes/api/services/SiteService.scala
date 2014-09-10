package org.openeyes.api.services

import org.openeyes.api.models.Site
import org.openeyes.api.persistence.{Sites, Couchbase}

import scala.concurrent.Future

/**
 * Created by stu on 02/09/2014.
 */
object SiteService {

  def createOne = {
    Sites.create
  }

  def find(id: String): Future[Option[Site]] = {
    Sites.find(id)
  }

  def findAll = {
    Sites.all
  }
}
