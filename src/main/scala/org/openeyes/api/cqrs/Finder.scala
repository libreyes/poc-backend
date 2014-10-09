package org.openeyes.api.cqrs

trait Finder[MT] {
  def find(q: Query): Seq[MT]
}
