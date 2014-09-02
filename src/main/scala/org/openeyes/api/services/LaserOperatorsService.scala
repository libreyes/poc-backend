package org.openeyes.api.services

import org.openeyes.api.data.LaserOperatorsData

/**
 * Created by stu on 02/09/2014.
 */
object LaserOperatorsService {

  def findAll = {
    LaserOperatorsData.laserOperators
  }
}
