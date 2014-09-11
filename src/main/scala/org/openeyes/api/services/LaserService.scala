package org.openeyes.api.services

import org.openeyes.api.data._
import org.openeyes.api.models.Laser

import scala.slick.driver.MySQLDriver.simple._

/**
 * Created by stu on 03/09/2014.
 */
object LaserService extends DatabaseSupport with SlickExtensions {

  def find(id: Int): Option[Laser] = {
    getConnection withSession { implicit session =>
      Tables.lasers.filter(_.id === id).firstOption
    }
  }

  def list: List[Laser] = {
    getConnection withSession { implicit session =>
      Tables.lasers.list
    }
  }

  def listForSite(siteId: Int): List[Laser] = {
    getConnection withSession { implicit session =>
      Tables.lasers.forSite(siteId).run(session).toList
    }
  }
}
