package org.openeyes.api.controllers

import org.json4s.mongo.ObjectIdSerializer
import org.json4s.{DefaultFormats, Formats}
import org.openeyes.api.stacks.ApiStack
import org.scalatra.swagger.Swagger

import scala.slick.driver.MySQLDriver.simple._
import Database.threadLocalSession
import org.openeyes.api.data.{Supplier, Suppliers, Coffees}
import org.scalatra.ScalatraServlet

/**
 * Created by stu on 09/09/2014.
 */
class SlickTrialController(db: Database)(implicit val swagger: Swagger) extends ApiStack {

  protected val applicationDescription = "The Site API."

  protected implicit val jsonFormats: Formats = DefaultFormats

  before() {
    contentType = formats("json")
  }

  get("/create-tables") {
    db withSession {
      (Suppliers.ddl ++ Coffees.ddl).create
    }
  }

  get("/load-data") {
    db withSession {
      // Insert some suppliers
      val sup1 = Supplier(None, "Acme, Inc.", "99 Market Street", "Groundsville", "CA", "95199")
      val sup2 = Supplier(None, "Superior Coffee", "1 Party Place", "Mendocino", "CA", "95460")
      val sup3 = Supplier(None,  "The High Ground", "100 Coffee Lane", "Meadows", "CA", "93966")

      Suppliers.insert(sup1)
      Suppliers.insert(sup2)
      Suppliers.insert(sup3)


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

  get("/suppliers") {
    db withSession {
      val q = Query(Suppliers)
      q.list
    }
  }

  get("/suppliers/:id") {
    db withSession {
      Suppliers.findById(params("id").toInt).first
    }
  }

}
