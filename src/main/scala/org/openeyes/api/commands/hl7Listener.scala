package org.openeyes.api.commands

import ca.uhn.hl7v2.DefaultHapiContext
import ca.uhn.hl7v2.app.Connection
import ca.uhn.hl7v2.app.ConnectionListener
import ca.uhn.hl7v2.parser.CanonicalModelClassFactory
import ca.uhn.hl7v2.protocol.ReceivingApplicationExceptionHandler
import org.openeyes.api.handlers.hl7._

object hl7Listener {
  def apply() = {
    val context = new DefaultHapiContext

    context.setModelClassFactory(new CanonicalModelClassFactory("2.6"))
    context.setValidationContext("ca.uhn.hl7v2.validation.impl.NoValidation")

    val server = context.newServer(10110, false)
    server.registerApplication("ADT", "A01", new AddPatient)
    server.startAndWait
  }
}
