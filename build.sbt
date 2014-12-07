import AssemblyKeys._

seq(assemblySettings: _*)

name := "json2props"

version := "0.0"

scalaVersion := "2.10.4"

scalacOptions ++= Seq("-unchecked", "-deprecation" , "-feature")

mainClass in assembly := Some("dummy.Dummy")

jarName in assembly := "json2props.jar"

libraryDependencies ++= Seq(
  "org.json4s"        %% "json4s-native" % "3.2.11"
)

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.2.+" % "test",
  "junit"          % "junit"     % "4.+"   % "test"
)

initialCommands in console := """import dummy._"""

sourceGenerators in Compile <+= 
 (sourceManaged in Compile, version, name, jarName in assembly) map {
  (dir, version, projectname, jarexe) =>
  val file = dir / "dummy" / "MetaInfo.scala"
  IO.write(file,
  """package dummy
    |object MetaInfo { 
    |  val version="%s"
    |  val project="%s"
    |  val jarbasename="%s"
    |}
    |""".stripMargin.format(version, projectname, jarexe.split("[.]").head) )
  Seq(file)
}
