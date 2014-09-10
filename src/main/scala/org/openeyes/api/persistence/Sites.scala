package org.openeyes.api.persistence

import com.couchbase.client.protocol.views.Query
import org.openeyes.api.models.Site
import play.api.libs.json._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


object Sites {

  implicit val siteFmt = Json.format[Site]

  def find(id: String): Future[Option[Site]] = {
    val result  = Couchbase.bucket.get[Site]("id-goes-here")
    result
  }

  def all: Future[List[Site]] = {
    val futureList: Future[List[Site]] = Couchbase.bucket.searchValues[Site]("Site", "moor")(new Query().setIncludeDocs(true)).toList
    futureList
  }

  def create = {
    val document = Site("id-goes-here", "bar", "blah", "baz")
    Couchbase.bucket.set[Site]("id-goes-here", document).onSuccess {
      case status => println(s"Operation status: ${status.getMessage}")
    }
  }

}
