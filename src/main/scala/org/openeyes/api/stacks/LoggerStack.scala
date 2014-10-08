package org.openeyes.api.stacks

/**
 * Created by dave on 19/08/2014.
 */
import org.slf4j.LoggerFactory

trait LoggerStack {
  val logger = LoggerFactory.getLogger(getClass)
}
