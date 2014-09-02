package org.openeyes.api.data

import org.openeyes.api.models.Site

/**
 * Created by stu on 02/09/2014.
 */
object SitesData {

  val all = List(
    Site(1, "City Road", None),
    Site(2, "Earling", None),
    Site(3, "Barking", None)
  )

  val lasers = List(
    List(), // Just to push the index up
    List(LasersData.all.apply(0), LasersData.all.apply(1), LasersData.all.apply(3)),
    List(LasersData.all.apply(2)),
    List(LasersData.all.apply(0), LasersData.all.apply(2))
  )

  // GROSS!!!
  def findLasers(id: String) = {
    val idx = id.toInt

    if(idx < 4){
      lasers.apply(id.toInt)
    }else{
      List()
    }
  }

}
