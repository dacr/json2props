name := "json2props"

version := "0.3-SNAPSHOT"

scalaVersion := "2.11.8"

scalacOptions ++= Seq("-unchecked", "-deprecation" , "-feature")

crossScalaVersions := Seq("2.10.6", "2.11.8")

libraryDependencies ++= Seq(
  "org.json4s"        %% "json4s-native" % "3.2.11"
)

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.1.6" % "test"
)

organization :="fr.janalyse"

organizationHomepage := Some(new URL("http://www.janalyse.fr"))

initialCommands in console := """
import fr.janalyse.json._
import JSon2Properties._
"""

