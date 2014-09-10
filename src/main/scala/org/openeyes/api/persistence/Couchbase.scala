package org.openeyes.api.persistence

import akka.actor.ActorSystem
import org.reactivecouchbase.{Configuration, ReactiveCouchbaseDriver}
import com.typesafe.config.ConfigFactory



/**
 * Created by dave on 09/09/2014.
 */
object Couchbase {

  val config =  new Configuration(ConfigFactory.load())
  lazy val driver = ReactiveCouchbaseDriver(ActorSystem(), config)
  lazy val bucket = driver.bucket("openeyes")

  def shutdown = {
    driver.shutdown()
  }

}
