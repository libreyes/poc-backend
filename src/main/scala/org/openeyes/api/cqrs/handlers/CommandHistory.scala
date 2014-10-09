package org.openeyes.api.cqrs.handlers.CommandHistory

import org.openeyes.api.models.CommandHistoryDao
import org.openeyes.api.cqrs._
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class CommandHistory @Autowired() (dao: CommandHistoryDao) extends CommandHandler[Command] {
  def handle(c: Command) = dao.save(c)
}
