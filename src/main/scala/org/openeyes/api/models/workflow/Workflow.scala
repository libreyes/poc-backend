package org.openeyes.api.models.workflow

import com.mongodb.casbah.MongoConnection
import com.novus.salat.annotations._
import com.novus.salat.annotations.raw.Key
import com.novus.salat.dao.{SalatDAO, ModelCompanion}
import com.novus.salat.global._
import org.bson.types.ObjectId

case class Workflow(@Key("_id") _id: ObjectId, name: String, steps: List[Step])

case class Step(name: String, components: List[Component])

@Salat
abstract class Component {
  def label: String
}

case class FormComponent(label: String, required: Boolean, hidden: Boolean) extends Component

case class ViewComponent(label: String) extends Component

object Workflow extends ModelCompanion[Workflow, ObjectId] {

  val collection = MongoConnection()("openeyes")("workflows")
  val dao = new SalatDAO[Workflow, ObjectId](collection = collection) {}
}
