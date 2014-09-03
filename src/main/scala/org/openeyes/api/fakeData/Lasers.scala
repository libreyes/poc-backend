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

  // GROSS!!!
  def forSite(id: String) = {
    try {
      val idx = id.toInt

      if (idx < 4) {
        all.apply(id.toInt)
      } else {
        List()
      }
    } catch {
      case e: Exception => List()
    }
  }
}
