package org.openeyes.api.cqrs.handlers

import org.openeyes.api.cqrs._
import org.openeyes.api.services.workflow.TicketService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class UpdateTicket @Autowired() (ticketService: TicketService) extends CommandHandler[commands.CreateEncounter] {
  def handle(c: commands.CreateEncounter) {
    if(c.encounter.ticketId.isDefined && c.encounter.stepIndex.isDefined){
      ticketService.updateStepIndexOrComplete(c.encounter.ticketId.get, c.encounter.stepIndex.get)
    }
  }
}
