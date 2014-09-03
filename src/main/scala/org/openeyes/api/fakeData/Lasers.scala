package org.openeyes.api.fakeData

import org.openeyes.api.models.Laser

/**
 * Created by stu on 02/09/2014.
 */
object Lasers {

  val all = List(
    Laser("1", "DS", "Death Star", "laser-1"),
    Laser("2", "MK", "Moonraker", "laser-2"),
    Laser("3", "MR", "Mind Ray", "laser-3"),
    Laser("4", "BM", "Beam", "laser-4")
  )

  val lasersForSite = List(
    List(), // Just to push the index up
    List(Lasers.all.apply(0), Lasers.all.apply(1), Lasers.all.apply(3)),
    List(Lasers.all.apply(2)),
    List(Lasers.all.apply(0), Lasers.all.apply(2))
  )

  // GROSS!!!
  def forSite(id: String) = {
    try {
      val idx = id.toInt

      if (idx < 4) {
        lasersForSite.apply(id.toInt)
      } else {
        List()
      }
    } catch {
      case e: Exception => List()
    }
  }
}
