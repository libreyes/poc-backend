package org.openeyes.api.persistence


import com.datastax.driver.core.Row
import com.websudos.phantom.Implicits._
import com.websudos.phantom.CassandraTable
import com.websudos.phantom.Implicits.{StringColumn, UUIDColumn}
import com.websudos.phantom.iteratee.Iteratee
import com.websudos.phantom.keys.PartitionKey
import org.openeyes.api.models.Site
import java.util.UUID


import org.openeyes.api.persistence.connectors.CassandraConnector

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._


//case class Site(id: String, codeValue: String, label: String, systemId: String)
sealed class SiteRecord extends CassandraTable[SiteRecord, Site] {
  object id extends UUIDColumn(this) with PartitionKey[UUID]
  object codeValue extends StringColumn(this)
  object label extends StringColumn(this)
  object systemId extends StringColumn(this)

  override def fromRow(row: Row): Site = {
    Site(id(row), codeValue(row), label(row), systemId(row))
  }
}

object SiteRecord extends SiteRecord with CassandraConnector {

  def createSite(site: Site) = {
    insert.value(_.id, site.id)
      .value(_.codeValue, site.codeValue)
      .value(_.label, site.label)
      .value(_.systemId, site.systemId).future()
  }

  def find(id: UUID): Future[Option[Site]] = {
    select.where(_.id eqs id).one()
  }

  def all: Future[Seq[Site]] = {
    select.fetchEnumerator() run Iteratee.collect()
  }

}

object SiteCreator {

//  def createSiteRecord = {
//    Await.ready(SiteRecord.create.future(), 2.seconds)
//  }

  def createSite = {
    val u = UUID.randomUUID
    val s = Site(u, "foo", "bar", "baz")
    val site = SiteRecord.createSite(s)
    site
  }
}