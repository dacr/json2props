name := "json2props"

organization :="fr.janalyse"
homepage := Some(new URL("https://github.com/dacr/json2props"))

scalaVersion := "2.12.8"
scalacOptions ++= Seq("-unchecked", "-deprecation" , "-feature")
crossScalaVersions := Seq("2.10.7", "2.11.12", "2.12.8", "2.13.0")

libraryDependencies ++= Seq(
  "org.json4s"        %% "json4s-jackson" % "3.6.7"
)

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.8" % "test"
)


libraryDependencies ++= {
  CrossVersion.partialVersion(scalaVersion.value) match {
    case Some((2, major)) if major > 10 =>
      Seq("org.scala-lang.modules" %% "scala-xml" % "1.2.0")
    case _ =>
      Seq()
  }
}

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

PgpKeys.useGpg in Global := true      // workaround with pgp and sbt 1.2.x
pgpSecretRing := pgpPublicRing.value  // workaround with pgp and sbt 1.2.x

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
 
