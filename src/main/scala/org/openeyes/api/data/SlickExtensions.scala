package org.openeyes.api.data

import org.openeyes.api.models.Laser

import scala.slick.lifted.{TableQuery, Query}

import scala.slick.driver.MySQLDriver.simple._

/**
 * Created by stu on 11/09/2014.
 */
trait SlickExtensions {

  implicit class LaserExtensions[C[_]](q: Query[Lasers, Laser, C]) {

    val siteLasers: TableQuery[SiteLasers] = TableQuery[SiteLasers]

    def forSite(siteId: Int) = {
      q.innerJoin(siteLasers).on(_.id === _.laserId).filter(_._2.siteId === siteId).map(things => things._1)
    }

  }
}
