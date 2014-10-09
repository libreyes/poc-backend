package org.openeyes.api.cqrs

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.support.AbstractApplicationContext
import org.springframework.stereotype.Component

@Component
class ReadDispatcher @Autowired() (ac: AbstractApplicationContext) {
  def dispatch[MT, IT](req: ReadRequest[MT, IT]) = {
    ac.getBean(classOf[ReadHandler[MT, IT]]).handle(req.id)
  }
}

