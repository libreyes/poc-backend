package org.openeyes.api.cqrs

trait ReadHandler[MT, IT] {
  def handle(id: IT): Option[MT]
}
