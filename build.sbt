name := "json2props"

organization :="fr.janalyse"
homepage := Some(new URL("https://github.com/dacr/json2props"))

scalaVersion := "2.11.8"
scalacOptions ++= Seq("-unchecked", "-deprecation" , "-feature")
crossScalaVersions := Seq("2.10.6", "2.11.8", "2.12.1")

libraryDependencies ++= Seq(
  "org.json4s"        %% "json4s-jackson" % "3.5.1"
)

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.1" % "test"
)


initialCommands in console := """
  |import org.json4s._
  |import org.json4s.JsonDSL._
  |import org.json4s.jackson.JsonMethods._
  |import fr.janalyse.json._
  |import JSon2Properties._
  |import Xml2Json._
  |""".stripMargin

