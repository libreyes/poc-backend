package org.openeyes.api.pg

import org.json4s.native.Serialization
import org.openeyes.api.models._
import scala.slick.driver.PostgresDriver.simple._

object Types {
  val dateTime = MappedColumnType.base[org.joda.time.DateTime, java.sql.Timestamp](
    { dt => new java.sql.Timestamp(dt.getMillis) },
    { ts => new org.joda.time.DateTime(ts) }
  )

  implicit val jsonFormats = new org.json4s.DefaultFormats {
    override val strict = true
    override val typeHintFieldName = "type"
    override val typeHints = org.json4s.FullTypeHints(List(classOf[Element]))
  }

  // FIXME: this probably needs to override sqlTypeName but I'm not sure how
  def json[T <: AnyRef : scala.reflect.Manifest]() = MappedColumnType.base[T, String](
    (Serialization.write[T](_)), (Serialization.read[T](_))
  )
}
