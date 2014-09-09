package org.openeyes.api.data.init

import com.mchange.v2.c3p0.ComboPooledDataSource

import scala.slick.session.Database

/**
 * Created by stu on 09/09/2014.
 */
object DatabaseAccess {

  def createDatasource: ComboPooledDataSource = {
    new ComboPooledDataSource
  }

  val myDS = createDatasource

  val db = Database.forDataSource(myDS)

  def closeDbConnection() {
    myDS.close
  }

}


trait SlickSupport {
  def getConnection = DatabaseAccess.db
}