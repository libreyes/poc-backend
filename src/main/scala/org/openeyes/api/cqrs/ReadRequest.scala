package org.openeyes.api.cqrs

case class ReadRequest[MT, IT](id: IT)
