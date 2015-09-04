name := "json2props"

version := "0.3"

scalaVersion := "2.11.7"

scalacOptions ++= Seq("-unchecked", "-deprecation" , "-feature")

crossScalaVersions := Seq("2.10.5", "2.11.7")

libraryDependencies ++= Seq(
  "org.json4s"        %% "json4s-jackson" % "3.2.+"
)

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.2.+" % "test"
)

organization :="fr.janalyse"

organizationHomepage := Some(new URL("http://www.janalyse.fr"))

initialCommands in console := """
  |import fr.janalyse.json._
  |import JSon2Properties._
  |""".stripMargin

