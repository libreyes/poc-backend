package org.openeyes.api.cqrs

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class CommandDispatcher @Autowired() (publisher: ApplicationEventPublisher) {
  def dispatch(c: Command) = {
    publisher.publishEvent(c)
  }
}
