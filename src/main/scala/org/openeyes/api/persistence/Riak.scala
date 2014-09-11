package org.openeyes.api.persistence

import com.scalapenos.riak.RiakClient

/**
 * Created by dave on 10/09/2014.
 */
trait RiakSupport {

  private lazy val client = RiakClient("localhost", 8098)
  val dataStore = client.bucket("openeyes")

}
