package org.openeyes.api.services

import org.openeyes.api.data.{SiteLasers, SlickExtensions, DatabaseSupport, Lasers}
import org.openeyes.api.models.Laser

import scala.slick.driver.MySQLDriver.simple._
import scala.slick.jdbc.{GetResult, StaticQuery => Q}

/**
 * Created by stu on 03/09/2014.
 */
object LaserService extends DatabaseSupport with SlickExtensions {

  val lasers: TableQuery[Lasers] = TableQuery[Lasers]
//  val siteLasers: TableQuery[SiteLasers] = TableQuery[SiteLasers]

  def find(id: Int): Option[Laser] = {
    getConnection withSession { implicit session =>
      lasers.filter(_.id === id).firstOption
    }
  }

  def list: List[Laser] = {
    getConnection withSession { implicit session =>
      lasers.list
    }
  }

  def listForSite(siteId: Int): List[Laser] = {
    getConnection withSession { implicit session =>
      lasers.forSite(siteId).run(session).toList
    }
  }
}
