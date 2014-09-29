package org.openeyes.api.handlers.hl7

import ca.uhn.hl7v2.model.Message
import ca.uhn.hl7v2.model.v21.message._
import ca.uhn.hl7v2.model.v21.segment._
import ca.uhn.hl7v2.protocol.ReceivingApplication
import org.bson.types.ObjectId
import org.openeyes.api.utils.Date._
import org.openeyes.api.models._
import org.openeyes.api.services._

class AddPatient extends ReceivingApplication {
  def canProcess(msg: Message) = true

  def processMessage(msg: Message, metadata: java.util.Map[String, AnyRef]) = {
    msg match {
      case m: ADT_A01 => {
        val pid = m.getPID
        val name = pid.getPATIENTNAME
        val addr = pid.getPATIENTADDRESS

        PatientService.create(Patient(
          new ObjectId,
          pid.getPATIENTIDINTERNALINTERNALID.getIDNumber.toString,
          name.getPrefix.toString,
          name.getGivenName.toString,
          name.getFamilyName.toString,
          pid.getDATEOFBIRTH.toString,
          pid.getSEX.toString,
          pid.getETHNICGROUP.toString,
          ContactDetail(None, Some(pid.getPHONENUMBERHOME(0).toString)),
          Some(Address(
            Some(addr.getStreetAddress.toString), Some(addr.getOtherDesignation.toString), Some(addr.getCity.toString), Some(addr.getStateOrProvince.toString), Some(addr.getZip.toString)
          )),
          Some(pid.getPATIENTIDEXTERNALEXTERNALID.getIDNumber.toString),
          None, None,
          pid.getPATIENTIDINTERNALINTERNALID.getIDNumber.toString
        ));
      }
      case _ => {
        throw new Exception("Unsupported message type")
      }
    }

    msg.generateACK
  }
}

