name         := "json2props"
organization := "fr.janalyse"
homepage     := Some(new URL("https://github.com/dacr/json2props"))
scmInfo      := Some(ScmInfo(url(s"https://github.com/dacr/json2props"), s"git@github.com:dacr/json2props.git"))

licenses += "NON-AI-APACHE2" -> url(s"https://github.com/non-ai-licenses/non-ai-licenses/blob/main/NON-AI-APACHE2")

scalaVersion := "3.3.0"
scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature")

crossScalaVersions := Seq("2.12.13", "2.13.11", "3.3.0")
// 2.11.x : generates java 6 bytecodes
// 2.12.x : generates java 8 bytecodes && JVM8 required for compilation
// 2.13.x : generates java 8 bytecodes && JVM8 required for compilation

libraryDependencies ++= Seq(
  "org.scala-lang.modules" %% "scala-xml"      % "2.1.0",
  "org.json4s"             %% "json4s-jackson" % "4.0.6",
  "org.scalatest"          %% "scalatest"      % "3.2.16" % Test
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
