package org.openeyes.api.db

import org.scalatra.ScalatraBase
import org.squeryl.{SessionFactory, Session}

/**
 * Created by stu on 08/09/2014.
 */
object DatabaseSessionSupport {

  val key = {
    val n = getClass.getName
    if (n.endsWith("$")) n.dropRight(1) else n
  }
}

trait DatabaseSessionSupport { this: ScalatraBase =>

  import DatabaseSessionSupport._

  def dbSession = request.get(key).orNull.asInstanceOf[Session]

  before() {
    request(key) = SessionFactory.newSession
    dbSession.bindToCurrentThread
  }

  after() {
    dbSession.close
    dbSession.unbindFromCurrentThread
  }
}
