package org.openeyes.api.fakeData

import org.openeyes.api.models.Site

/**
 * Created by stu on 02/09/2014.
 */
object Sites {

  val all = List(
    Site("1", "CR", "City Road", "site-1"),
    Site("2", "EG", "Ealing", "site-2"),
    Site("3", "BG", "Barking", "site-3")
  )

  val lasers = List(
    List(), // Just to push the index up
    List(Lasers.all.apply(0), Lasers.all.apply(1), Lasers.all.apply(3)),
    List(Lasers.all.apply(2)),
    List(Lasers.all.apply(0), Lasers.all.apply(2))
  )

}
