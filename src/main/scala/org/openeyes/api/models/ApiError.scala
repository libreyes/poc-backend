package org.openeyes.api.models

/**
 * Created by dave on 19/08/14.
 */

case class ApiError(var message: String) {
  // TODO: Probably a nicer way to do this
  // This is in case the message thrown is a MappingException that might contain '\n' in its message
  message = message.replace("\n", ". ")
}
