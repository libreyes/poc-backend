package org.openeyes.api.views.observations

import org.json4s.JsonAST.JValue
import org.openeyes.api.models.Observation
import org.json4s.JsonDSL._


/**
 * Created by dave on 20/08/2014.
 */
class ObservationList(observations: Seq[Observation])

object ObservationList {

  def apply(observations: Seq[Observation]): JValue = {
    val ast = observations.map { obs =>
      (
        ("id" -> obs._id.toString) ~
          ("weight" -> obs.weight.grams)
        )
    }
    ast
  }

}
