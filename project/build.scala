import sbt._
import Keys._
import org.scalatra.sbt._
import com.mojolly.scalate.ScalatePlugin._
import sbtbuildinfo.Plugin._

object OpenEyesBuild extends Build {
  val Organization = "org.openeyes"
  val Name = "Openeyes"
  val Version = "0.1.5"
  val ScalaVersion = "2.11.1"
  val ScalatraVersion = "2.3.0"

  lazy val project = Project (
    "openeyes",
    file("."),
    settings = buildInfoSettings ++
      Seq(
        sourceGenerators in Compile <+= buildInfo,
        buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),
        buildInfoPackage := "openeyesApi"
      ) ++ ScalatraPlugin.scalatraWithJRebel ++ scalateSettings ++ Seq(
      organization := Organization,
      name := Name,
      version := Version,
      scalaVersion := ScalaVersion,
      resolvers ++= Seq(
        Classpaths.typesafeReleases,
        "amateras-repo" at "http://amateras.sourceforge.jp/mvn/"
      ),
      libraryDependencies ++= Seq(
        "ca.uhn.hapi" % "hapi-base" % "2.2",
        "ca.uhn.hapi" % "hapi-structures-v21" % "2.2",
        "ca.uhn.hapi" % "hapi-structures-v22" % "2.2",
        "ca.uhn.hapi" % "hapi-structures-v23" % "2.2",
        "ca.uhn.hapi" % "hapi-structures-v231" % "2.2",
        "ca.uhn.hapi" % "hapi-structures-v24" % "2.2",
        "ca.uhn.hapi" % "hapi-structures-v25" % "2.2",
        "ca.uhn.hapi" % "hapi-structures-v251" % "2.2",
        "ca.uhn.hapi" % "hapi-structures-v26" % "2.2",
        "com.novus" %% "salat" % "1.9.8",
        "commons-codec" % "commons-codec" % "1.9",
        "commons-io" % "commons-io" % "2.0.1",
        "org.im4java" % "im4java" % "1.4.0",
        "org.json4s"   %% "json4s-jackson" % "3.2.9",
        "org.json4s" %% "json4s-mongo" % "3.2.9",
        "org.scalatra" %% "scalatra" % ScalatraVersion,
        "org.scalatra" %% "scalatra-json" % ScalatraVersion,
        "org.scalatra" %% "scalatra-scalate" % ScalatraVersion,
        "org.scalatra" %% "scalatra-specs2" % ScalatraVersion % "test",
        "org.scalatra" %% "scalatra-swagger"  % ScalatraVersion,
        "org.eclipse.jetty" % "jetty-webapp" % "9.1.3.v20140225" % "container",
        "org.eclipse.jetty" % "jetty-plus" % "9.1.3.v20140225" % "container",
        "org.eclipse.jetty.orbit" % "javax.servlet" % "3.0.0.v201112011016" % "container;provided;test" artifacts (Artifact("javax.servlet", "jar", "jar"))
      )
    )
  )
}
