package org.openeyes.api.services

import org.openeyes.api.fakeData.LaserOperators

/**
 * Created by stu on 02/09/2014.
 */
object LaserOperatorService {

  def findAll = {
    LaserOperators.all
  }
}
