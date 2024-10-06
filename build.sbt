name         := "json2props"
organization := "fr.janalyse"
description  := "Hack to translate some JSON data to properties like data (key-value)"

licenses += "NON-AI-APACHE2" -> url(s"https://github.com/non-ai-licenses/non-ai-licenses/blob/main/NON-AI-APACHE2")

scalaVersion       := "3.3.0"
crossScalaVersions := Seq("2.13.15", "3.5.1")

scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature")

libraryDependencies ++= Seq(
  "org.scala-lang.modules" %% "scala-xml"      % "2.3.0",
  "org.json4s"             %% "json4s-jackson" % "4.0.7",
  "org.scalatest"          %% "scalatest"      % "3.2.19" % Test
)

Test / testOptions += {
  val rel = scalaVersion.value.split("[.]").take(2).mkString(".")
  Tests.Argument(
    "-oDF", // -oW to remove colors
    "-u",
    s"target/junitresults/scala-$rel/"
  )
}

console / initialCommands := """
                               |import org.json4s._
                               |import org.json4s.JsonDSL._
                               |import org.json4s.jackson.JsonMethods._
                               |import fr.janalyse.json._
                               |import JSon2Properties._
                               |import Xml2Json._
                               |""".stripMargin

homepage   := Some(url("https://github.com/dacr/json2props"))
scmInfo    := Some(ScmInfo(url(s"https://github.com/dacr/json2props"), s"git@github.com:dacr/json2props.git"))
developers := List(
  Developer(
    id = "dacr",
    name = "David Crosson",
    email = "crosson.david@gmail.com",
    url = url("https://github.com/dacr")
  )
)
