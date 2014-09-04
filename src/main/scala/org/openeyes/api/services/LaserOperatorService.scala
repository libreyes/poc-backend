package org.openeyes.api.services

import org.openeyes.api.fakeData.LaserOperators

/**
 * Created by stu on 02/09/2014.
 */
object LaserOperatorService {

  def find(id: String) = {
    LaserOperators.all.find(s => s.id == id) match {
      case Some(laser) => Some(laser)
      case None => None
    }
  }

  def findAll = {
    LaserOperators.all
  }
}
