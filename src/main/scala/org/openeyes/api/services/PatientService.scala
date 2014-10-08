package org.openeyes.api.services

import org.bson.types.ObjectId
import org.openeyes.api.models._
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Created by stu on 02/09/2014.
 */
@Service
class PatientService @Autowired() (patientDao: PatientDao) {
  def find(id: String): Option[Patient] = {
    patientDao.findOneById(new ObjectId(id))
  }

  def findAll = {
    patientDao.findAll().toSeq
  }

  def search(searchTerm: String) = {
    patientDao.search(searchTerm)
  }

  def create(patient: Patient) {
    patientDao.save(patient)
  }
}
