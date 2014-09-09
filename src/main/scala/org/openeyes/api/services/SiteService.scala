package org.openeyes.api.services

import org.openeyes.api.data.Sites
import org.openeyes.api.data.init.SlickSupport
import org.openeyes.api.models.Site

import scala.slick.driver.MySQLDriver.simple._
import Database.threadLocalSession

import scala.slick.lifted.Query

/**
 * Created by stu on 02/09/2014.
 */
object SiteService extends SlickSupport {

  def find(id: Int): Option[Site] = {
    getConnection withSession {
      val q = Sites.findById(id)
      q.firstOption
    }
  }

  def findAll = {
    getConnection withSession {
      val q = Query(Sites)
      q.list
    }
  }
}
