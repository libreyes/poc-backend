package org.openeyes.api.services

import org.openeyes.api.fakeData.Procedures

/**
 * Created by stu on 04/09/2014.
 */
object ProcedureService {

  def find(id: String) = {
    Procedures.all.find(p => p.id == id) match {
      case Some(procedure) => Some(procedure)
      case None => None
    }
  }

  def findAll = {
    Procedures.all
  }
}
