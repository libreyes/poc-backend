package org.openeyes.api.data

import com.mchange.v2.c3p0.ComboPooledDataSource

import scala.slick.driver.MySQLDriver.simple._

/**
 * Created by stu on 09/09/2014.
 */
object DatabaseAccess {

  def createDatasource: ComboPooledDataSource = {
    new ComboPooledDataSource
  }

  val dataSource = createDatasource
  val database = Database.forDataSource(dataSource)

  def closeDbConnection() {
    dataSource.close
  }
}

trait DatabaseSupport {
  def getConnection = DatabaseAccess.database
}