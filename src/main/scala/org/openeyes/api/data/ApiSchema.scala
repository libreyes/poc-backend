package org.openeyes.api.data

import org.openeyes.api.models.Site
import org.squeryl.PrimitiveTypeMode._
import org.squeryl.Schema

/**
 * Created by stu on 09/09/2014.
 */
object ApiSchema extends Schema {
  val sites = table[Site]("site")

  on(sites)(s => declare(
    s.id is(autoIncremented, primaryKey)
  ))
}

