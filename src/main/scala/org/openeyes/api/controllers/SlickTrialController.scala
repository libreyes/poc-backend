package org.openeyes.api.controllers

import scala.slick.driver.H2Driver.simple._
import Database.threadLocalSession
import org.openeyes.api.data.{Suppliers, Coffees}
import org.scalatra.ScalatraServlet

/**
 * Created by stu on 09/09/2014.
 */
class SlickTrialController(db: Database) extends ScalatraServlet {

  get("/create-tables") {
    db withSession {
      (Suppliers.ddl ++ Coffees.ddl).create
    }
  }

  get("/load-data") {
    db withSession {
      // Insert some suppliers
      Suppliers.insert(101, "Acme, Inc.", "99 Market Street", "Groundsville", "CA", "95199")
      Suppliers.insert(49, "Superior Coffee", "1 Party Place", "Mendocino", "CA", "95460")
      Suppliers.insert(150, "The High Ground", "100 Coffee Lane", "Meadows", "CA", "93966")

      // Insert some coffees (using JDBC's batch insert feature, if supported by the DB)
      Coffees.insertAll(
        ("Colombian", 101, 7.99, 0, 0),
        ("French_Roast", 49, 8.99, 0, 0),
        ("Espresso", 150, 9.99, 0, 0),
        ("Colombian_Decaf", 101, 8.99, 0, 0),
        ("French_Roast_Decaf", 49, 9.99, 0, 0)
      )
    }
  }

  get("/drop-tables") {
    db withSession {
      (Suppliers.ddl ++ Coffees.ddl).drop
    }
  }

  get("/coffees") {
    db withSession {
      val q3 = for {
        c <- Coffees
        s <- c.supplier
      } yield (c.name.asColumnOf[String], s.name.asColumnOf[String])

      contentType = "text/html"
      q3.list.map { case (s1, s2) => "  " + s1 + " supplied by " + s2 } mkString "<br />"
    }
  }

}
