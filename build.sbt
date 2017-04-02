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




pomIncludeRepository := { _ => false }

useGpg := true

licenses += "Apache 2" -> url(s"http://www.apache.org/licenses/LICENSE-2.0.txt")
releaseCrossBuild := true
releasePublishArtifactsAction := PgpKeys.publishSigned.value
publishMavenStyle := true
publishArtifact in Test := false
publishTo := Some(if (isSnapshot.value) Opts.resolver.sonatypeSnapshots else Opts.resolver.sonatypeStaging)

scmInfo := Some(ScmInfo(url(s"https://github.com/dacr/json2props"), s"git@github.com:dacr/json2props.git"))

pomExtra in Global := {
  <developers>
    <developer>
      <id>dacr</id>
      <name>David Crosson</name>
      <url>https://github.com/dacr</url>
    </developer>
  </developers>
}


import ReleaseTransformations._
releaseProcess := Seq[ReleaseStep](
    checkSnapshotDependencies,
    inquireVersions,
    //runClean,
    runTest,
    setReleaseVersion,
    commitReleaseVersion,
    tagRelease,
    publishArtifacts,
    setNextVersion,
    commitNextVersion,
    releaseStepCommand("sonatypeReleaseAll"),
    pushChanges
  )
 