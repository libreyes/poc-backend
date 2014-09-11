package org.openeyes.api.services

import org.openeyes.api.models.Site
import org.openeyes.api.persistence.SiteRecord
import java.util.UUID

import scala.concurrent.Future

/**
 * Created by stu on 02/09/2014.
 */
object SiteService {

  def find(id: String): Future[Option[Site]] = {
    val uuid = UUID.fromString(id)
    SiteRecord.find(uuid)
  }

  def findAll = {
//    Sites.all
  }

  def create = {

  }
}
