package org.openeyes.api.persistence.connectors

import com.datastax.driver.core.Cluster
import com.datastax.driver.core.Session

import scala.concurrent. { blocking, Future }
import com.datastax.driver.core.{ Cluster, Session }
import com.websudos.phantom.Implicits._

object CassandraConnector {
  val keySpace = "openeyes"

  lazy val cluster =  Cluster.builder()
    .addContactPoint("localhost")
    .withPort(9042)
    .withoutJMXReporting()
    .withoutMetrics()
    .build()

  lazy val session = blocking {
    cluster.connect(keySpace)
  }
}

trait CassandraConnector {
  self: CassandraTable[_, _] =>

  def createTable(): Future[Unit] ={
    create.future() map (_ => ())
  }

  implicit lazy val datastax: Session = CassandraConnector.session
}
