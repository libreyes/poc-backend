package org.openeyes.api.Utilities

import java.text.SimpleDateFormat

/**
 * Created by stu on 05/09/2014.
 */
object Date {

  def setTimestamp = {
    System.currentTimeMillis
  }

  def timestampToShortDate(timestamp: Long) = {
    val simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy")
    simpleDateFormat.format(timestamp)
  }
}
