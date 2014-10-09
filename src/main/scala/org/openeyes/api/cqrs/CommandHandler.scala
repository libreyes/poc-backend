package org.openeyes.api.cqrs

import org.springframework.context.ApplicationListener

trait CommandHandler[CT <: Command] extends ApplicationListener[CT] {
  def handle(c: CT)
  def onApplicationEvent(c: CT) = handle(c)
}
