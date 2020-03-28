name := "json2props"
organization :="fr.janalyse"
homepage := Some(new URL("https://github.com/dacr/json2props"))
licenses += "Apache 2" -> url(s"http://www.apache.org/licenses/LICENSE-2.0.txt")
scmInfo := Some(ScmInfo(url(s"https://github.com/dacr/json2props"), s"git@github.com:dacr/json2props.git"))

scalaVersion := "2.13.1"
scalacOptions ++= Seq( "-deprecation", "-unchecked", "-feature")

crossScalaVersions := Seq("2.11.12", "2.12.11", "2.13.1")
// 2.11.12 : generates java 6 bytecodes
// 2.12.11 : generates java 8 bytecodes && JVM8 required for compilation
// 2.13.1  : generates java 8 bytecodes && JVM8 required for compilation

libraryDependencies ++= Seq(
  "org.scala-lang.modules" %% "scala-xml" % "1.3.0",
  "org.json4s"    %% "json4s-jackson" % "3.6.7",
  "org.scalatest" %% "scalatest" % "3.1.1" % Test
)


testOptions in Test += {
  val rel = scalaVersion.value.split("[.]").take(2).mkString(".")
  Tests.Argument(
    "-oDF", // -oW to remove colors
    "-u", s"target/junitresults/scala-$rel/"
  )
}



initialCommands in console := """
  |import org.json4s._
  |import org.json4s.JsonDSL._
  |import org.json4s.jackson.JsonMethods._
  |import fr.janalyse.json._
  |import JSon2Properties._
  |import Xml2Json._
  |""".stripMargin
