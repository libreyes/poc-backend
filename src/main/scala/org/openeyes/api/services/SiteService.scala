package org.openeyes.api.services

import org.openeyes.api.data.Sites
import org.openeyes.api.data.init.SlickSupport

import scala.slick.driver.MySQLDriver.simple._
import Database.threadLocalSession

import scala.slick.lifted.Query

/**
 * Created by stu on 02/09/2014.
 */
object SiteService extends SlickSupport {

  def find(id: String) = {
//    Sites.all.find(s => s.id == id) match {
//      case Some(site) => Some(site)
//      case None => None
//    }
  }

  def findAll = {
    getConnection withSession {
        val q = Query(Sites)
        q.list
    }
  }
}
