package org.openeyes.api.fakeData

import org.openeyes.api.models.Site

/**
 * Created by stu on 02/09/2014.
 */
object Sites {

  val all = List(
    Site(1, "City Road", None),
    Site(2, "Earling", None),
    Site(3, "Barking", None)
  )

  val lasers = List(
    List(), // Just to push the index up
    List(Lasers.all.apply(0), Lasers.all.apply(1), Lasers.all.apply(3)),
    List(Lasers.all.apply(2)),
    List(Lasers.all.apply(0), Lasers.all.apply(2))
  )

  // GROSS!!!
  def findLasers(id: String) = {
    try {
      val idx = id.toInt

      if(idx < 4){
        lasers.apply(id.toInt)
      }else{
        List()
      }
    } catch {
      case e: Exception => List()
    }
  }

}
