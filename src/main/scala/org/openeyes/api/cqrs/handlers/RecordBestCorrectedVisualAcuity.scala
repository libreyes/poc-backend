package org.openeyes.api.cqrs.handlers

import org.openeyes.api.cqrs._
import org.openeyes.api.models.{Element,VisualAcuity,VisualAcuitySide}
import org.openeyes.api.models.read.{BestCorrectedVisualAcuity,BestCorrectedVisualAcuityDao}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class RecordBestCorrectedVisualAcuity @Autowired() (dao: BestCorrectedVisualAcuityDao) extends CommandHandler[commands.CreateEncounter] {
  def handle(c: commands.CreateEncounter) = {

    def transform(s: VisualAcuitySide, side: String) = BestCorrectedVisualAcuity(
      c.encounter.patientId.toString,
      c.encounter.createdAt,
      side,
      s.readings map (_.value) reduceLeft (_ max _)
    )

    (c.encounter.elements map ((e: Element) =>
      e match {
        case VisualAcuity(le, re) => Seq(le map (transform(_, "left")), re map (transform(_, "right")))
        case _ => Nil
      }
    )).flatten.flatten foreach (dao.save(_))
  }
}

