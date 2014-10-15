package org.openeyes.api.config

import com.typesafe.config.Config

/**
 * Created by stu on 14/10/2014.
 */
class AppConfig(config: Config) {

  val database = config.getString("app.db")
}
