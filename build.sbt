name := "json2props"

version := "0.2"

scalaVersion := "2.11.6"

scalacOptions ++= Seq("-unchecked", "-deprecation" , "-feature")

crossScalaVersions := Seq("2.10.5", "2.11.6")

libraryDependencies ++= Seq(
  "org.json4s"        %% "json4s-native" % "3.2.+"
)

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.1.+" % "test"
)

organization :="fr.janalyse"

organizationHomepage := Some(new URL("http://www.janalyse.fr"))

initialCommands in console := """
import fr.janalyse.json._
import JSon2Properties._
"""

publishTo := Some(
     Resolver.sftp(
         "JAnalyse Repository",
         "www.janalyse.fr",
         "/home/tomcat/webapps-janalyse/repository"
     ) as("tomcat", new File(util.Properties.userHome+"/.ssh/id_rsa"))
)
