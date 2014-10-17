package org.openeyes.api.models.workflow

import com.mongodb.casbah.MongoConnection
import com.novus.salat.annotations._
import com.novus.salat.annotations.raw.Key
import com.novus.salat.dao.{SalatDAO, ModelCompanion}
import com.novus.salat.global._
import com.typesafe.config.ConfigFactory
import org.bson.types.ObjectId
import org.openeyes.api.config.AppConfig

case class Workflow(@Key("_id") _id: ObjectId, name: String, site: String, steps: List[Step])

case class Step(name: String, role: String, components: List[Component])

@Salat
abstract class Component {
  def name: String
  def label: String
}

case class FormComponent(name: String, label: String, required: Boolean = true, hidden: Boolean = false) extends Component

case class ViewComponent(name: String, label: String) extends Component

object Workflow extends ModelCompanion[Workflow, ObjectId] {

  val config = new AppConfig(ConfigFactory.load())
  val collection = MongoConnection()(config.database)("workflows")
  val dao = new SalatDAO[Workflow, ObjectId](collection = collection) {}
}
