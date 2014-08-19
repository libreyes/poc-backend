package org.openeyes.api

import org.scalatra._
import scalate.ScalateSupport

class FooController extends OpeneyesStack {

  get("/") {
    <html>
      <body>
        <h1>Hello, world!</h1>
        Say <a href="hello-scalate">hello to Scalate</a>.
      </body>
    </html>
  }
  
}
