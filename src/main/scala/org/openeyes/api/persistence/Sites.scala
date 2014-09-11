package org.openeyes.api.persistence

import com.scalapenos.riak.RiakClient
import org.openeyes.api.models.Site

/**
 * Created by stu on 02/09/2014.
 */
object Sites extends RiakSupport {

  def create(id: String, site: Site) = {
    dataStore.storeAndFetch(id, site)
  }

  def find(id: String) = {
    dataStore.fetch(id)
  }

}
