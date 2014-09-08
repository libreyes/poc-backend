package org.openeyes.api

import org.squeryl.PrimitiveTypeMode._
import org.squeryl.{Query, Schema}
import org.squeryl.annotations.Column

/**
 * Created by stu on 08/09/2014.
 */

case class Site(id: Int,
                @Column("short_name") val codeValue: String,
                @Column("name") val label: String,
                @Column("remote_id") val systemId: String)

object SiteDao extends Schema {
  val sites = table[Site]("sites")

  on(sites)(s => declare(
    s.id is(autoIncremented, primaryKey)
  ))
}

object Site {

  def findAll = {
    from(SiteDao.sites)(s => select(s)).toList
  }
}