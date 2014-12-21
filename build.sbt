name := "json2props"

version := "0.1"

scalaVersion := "2.11.4"

scalacOptions ++= Seq("-unchecked", "-deprecation" , "-feature")

crossScalaVersions := Seq("2.10.4", "2.11.4")

libraryDependencies ++= Seq(
  "org.json4s"        %% "json4s-native" % "3.2.11"
)

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.1.+" % "test"
)

initialCommands in console := """
import fr.janalyse.json._
import JSon2Properties
"""

publishTo := Some(
     Resolver.sftp(
         "JAnalyse Repository",
         "www.janalyse.fr",
         "/home/tomcat/webapps-janalyse/repository"
     ) as("tomcat", new File(util.Properties.userHome+"/.ssh/id_rsa"))
)