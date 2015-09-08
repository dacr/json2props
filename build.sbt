name := "json2props"

version := "0.4-SNAPSHOT"

scalaVersion := "2.11.7"

scalacOptions ++= Seq("-unchecked", "-deprecation" , "-feature")

crossScalaVersions := Seq("2.10.5", "2.11.7")

libraryDependencies ++= Seq(
  //"org.json4s"        %% "json4s-jackson" % "3.3.0.RC4"
  "org.json4s"        %% "json4s-jackson" % "3.2.+"
  //"org.json4s"        %% "json4s-jackson" % "3.3.0-SNAPSHOT"
)

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.2.+" % "test"
)

organization :="fr.janalyse"

organizationHomepage := Some(new URL("http://www.janalyse.fr"))

initialCommands in console := """
  |import org.json4s._
  |import org.json4s.JsonDSL._
  |import org.json4s.jackson.JsonMethods._
  |import fr.janalyse.json._
  |import JSon2Properties._
  |import Xml2Json._
  |""".stripMargin

resolvers += "JAnalyse repository" at "http://www.janalyse.fr/repository"
