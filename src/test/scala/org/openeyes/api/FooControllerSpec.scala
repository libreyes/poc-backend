package org.openeyes.api

import org.openeyes.api.controllers.FooController
import org.scalatra.test.specs2._

// For more on Specs2, see http://etorreborre.github.com/specs2/guide/org.specs2.guide.QuickStart.html
class FooControllerSpec extends ScalatraSpec { def is =
  "GET / on FooController"                     ^
    "should return status 200"                  ! root200^
                                                end

  addServlet(classOf[FooController], "/*")

  def root200 = get("/") {
    status must_== 200
  }
}
