package org.openeyes.api.services

import org.openeyes.api.data._
import org.openeyes.api.models.Site

import scala.slick.driver.MySQLDriver.simple._

/**
 * Created by stu on 02/09/2014.
 */
object SiteService extends DatabaseSupport {

  def find(id: Int): Option[Site] = {
    getConnection withSession { implicit session =>
      Tables.sites.filter(_.id === id).firstOption
    }
  }

  def list: List[Site] = {
    getConnection withSession { implicit session =>
      Tables.sites.list
    }
  }
}
