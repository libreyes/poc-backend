package org.openeyes.api.cqrs.commands

import org.openeyes.api.cqrs._
import org.openeyes.api.models.Encounter

case class CreateEncounter(encounter: Encounter) extends Command
