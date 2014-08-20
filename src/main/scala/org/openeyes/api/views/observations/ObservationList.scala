package org.openeyes.api.views.observations

import org.openeyes.api.models.Observation


/**
 * An experiment in building JSON views using case classes and companion
 * objects. Assuming that we had val observations = FooService.listAll: Seq[Observation],
 * we can get back a rendered JSON view by simply doing:
 *
 *   ObservationList(observations)
 *
 * This is handy, and works - we can render the output however we want, to trim
 * out (in this case) the ugly BSON object id, or get rid of the weight object
 * (as below) and go straight to obs.weight.grams. This is just an example to
 * try it out, in actuality we'd probably still want that Weight object in between.
 *
 * This code works fine, but it messes up the Swagger docs. If we specify the
 * output of the Swagger apiOperation as List[Observation], the output of course
 * looks nothing like what we get from this code. We'd need a way to pass this type
 * to Swagger and have it work, then we're golden here.
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
