package org.openeyes.api.handlers.hl7

import ca.uhn.hl7v2.model.Message
import ca.uhn.hl7v2.model.v26.message._
import ca.uhn.hl7v2.protocol.ReceivingApplication
import org.bson.types.ObjectId
import org.openeyes.api.di.ProductionEnvironment
import org.openeyes.api.utils.Date._
import org.openeyes.api.models._

class AddPatient extends ReceivingApplication {
  private val env = ProductionEnvironment

  def canProcess(msg: Message) = true

  def processMessage(msg: Message, metadata: java.util.Map[String, AnyRef]) = {
    msg match {
      case m: ADT_A01 => {
        val pid = m.getPID
        val name = pid.getPatientName(0)
        val addr = pid.getPatientAddress(0)

        env.patientService.create(Patient(
          new ObjectId,
          pid.getPatientIdentifierList(0).getIDNumber.toString,
          name.getPrefixEgDR.toString,
          name.getGivenName.toString,
          name.getFamilyName.toString,
          pid.getDateTimeOfBirth.toString,
          pid.getAdministrativeSex.toString,
          pid.getEthnicGroup.toString,
          ContactDetail(None, Some(pid.getPhoneNumberHome(0).toString)),
          Some(Address(
            Some(addr.getStreetAddress.toString), Some(addr.getOtherDesignation.toString), Some(addr.getCity.toString), Some(addr.getStateOrProvince.toString), Some(addr.getZipOrPostalCode.toString)
          )),
          None, None, None,
          pid.getPatientIdentifierList(0).getIDNumber.toString
        ));
      }
      case _ => {
        throw new Exception("Unsupported message type")
      }
    }

    msg.generateACK
  }
}

