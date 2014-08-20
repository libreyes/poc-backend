package org.openeyes.api.views.observations

import org.openeyes.api.models.Observation


/**
 * Created by dave on 20/08/2014.
 */
class ObservationList(observations: Seq[Observation])

object ObservationList {
  import org.json4s.JsonDSL._
  import org.json4s._
  import org.json4s.jackson.JsonMethods._


  def apply(observations: Seq[Observation]): JValue = {
    val ast = observations.map { obs =>
      (
        ("id" -> obs._id.toString) ~
          ("weight" -> obs.weight.grams)
        )
    }
    render(ast)
  }

}
