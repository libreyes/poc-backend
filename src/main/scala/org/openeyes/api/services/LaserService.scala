package org.openeyes.api.services

import org.openeyes.api.data.{DatabaseSupport, Lasers}
import org.openeyes.api.fakeData.Lasers

import scala.slick.driver.MySQLDriver.simple._

/**
 * Created by stu on 03/09/2014.
 */
object LaserService extends DatabaseSupport {

  val lasers: TableQuery[Lasers] = TableQuery[Lasers]

  def find(id: Int) = {
    getConnection withSession { implicit session =>
      lasers.filter { _.id === id }.firstOption
    }
  }

  def list = {
    getConnection withSession { implicit session =>
      lasers.list
    }
  }

  def findAllForSite(siteId: String) = {
//    Lasers.forSite(siteId)
  }
}
