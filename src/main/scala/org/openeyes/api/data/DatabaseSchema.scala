package org.openeyes.api.data

import org.openeyes.api.models._
import org.squeryl.PrimitiveTypeMode._
import org.squeryl.Schema

/**
 * Created by stu on 09/09/2014.
 */
object DatabaseSchema extends Schema {

  val lasers = table[Laser]("laser")
  val sites = table[Site]("site")

  on(lasers)(o => declare(
    o.id is(autoIncremented, primaryKey)
  ))

  on(sites)(o => declare(
    o.id is(autoIncremented, primaryKey)
  ))

  val siteLasers = manyToManyRelation(sites, lasers).
    via[SiteLaser]((s, l, sl) => (sl.siteId === s.id, l.id === sl.laserId))

  override def columnNameFromPropertyName(n:String) =
    NamingConventionTransforms.snakify(n)

  override def tableNameFromClassName(n:String) =
    NamingConventionTransforms.snakify(n)
}

