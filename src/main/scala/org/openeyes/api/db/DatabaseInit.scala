package org.openeyes.api.db

import java.util.Properties

import com.mchange.v2.c3p0.ComboPooledDataSource
import org.slf4j.LoggerFactory
import org.squeryl.adapters.MySQLAdapter
import org.squeryl.{Session, SessionFactory}

/**
 * Created by stu on 08/09/2014.
 */
trait DatabaseInit {

  val logger = LoggerFactory.getLogger(getClass)
  var cpds = new ComboPooledDataSource

  def configureDb() {
    val props = new Properties
    props.load(getClass.getResourceAsStream("/c3p0.properties"))
    cpds.setProperties(props)

    SessionFactory.concreteFactory = Some(() => connection)

    def connection = {
      logger.info("Creating connection with c3po connection pool")
      Session.create(cpds.getConnection, new MySQLAdapter)
    }
  }

  def closeDbConnection() {
    logger.info("Closing c3po connection pool")
    cpds.close()
  }
}
