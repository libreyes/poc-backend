package org.openeyes.api.data

import com.mchange.v2.c3p0.ComboPooledDataSource

import scala.slick.driver.MySQLDriver.simple._

/**
 * Created by stu on 09/09/2014.
 */
object Access {

  def createDatasource: ComboPooledDataSource = {
    new ComboPooledDataSource
  }

  val dataSource = createDatasource
  val database = Database.forDataSource(dataSource)

  def closeDbConnection() {
    dataSource.close
  }
}

trait Support {
  def getConnection = Access.database
}