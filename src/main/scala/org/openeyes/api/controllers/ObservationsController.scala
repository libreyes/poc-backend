package org.openeyes.api.controllers

import org.openeyes.api.stacks.ScalateStack

class ObservationsController extends ScalateStack {

  get("/") {
    <html>
      <body>
        <h1>Hello, world!</h1>
        Say <a href="hello-scalate">hello to Scalate</a>.
      </body>
    </html>
  }
  
}
