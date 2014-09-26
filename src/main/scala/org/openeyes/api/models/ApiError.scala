package org.openeyes.api.models

/**
 * Created by dave on 19/08/14.
 */

case class ApiError(var message: String) {
  // TODO: Probably a nicer way to do this
  message = message.replace("\n", ". ")
}
