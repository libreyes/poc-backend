package org.openeyes.api.persistence

import com.scalapenos.riak.RiakClient

/**
 * Created by dave on 10/09/2014.
 */
object Riak {

  private lazy val client = RiakClient("localhost", 8098)
  val bucket = client.bucket("openeyes")

}
