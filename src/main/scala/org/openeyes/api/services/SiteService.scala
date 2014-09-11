package org.openeyes.api.services

import org.openeyes.api.data.{DatabaseSupport, Sites}
import org.openeyes.api.models.Site

import scala.slick.driver.MySQLDriver.simple._

/**
 * Created by stu on 02/09/2014.
 */
object SiteService extends DatabaseSupport {

  val sites: TableQuery[Sites] = TableQuery[Sites]

  def find(id: Int): Option[Site] = {
    getConnection withSession { implicit session =>
      sites.filter(_.id === id).firstOption
    }
  }

  def list: List[Site] = {
    getConnection withSession { implicit session =>
      sites.list
    }
  }
}
