import sbtassembly.AssemblyPlugin.defaultShellScript

lazy val assemblySettings = Seq(
  test in assembly := {},
  assemblyJarName in assembly := "mal.jar",
  assemblyOption in assembly := (assemblyOption in assembly).value.copy(prependShellScript = Some(defaultShellScript))
)

lazy val root = (project in file(".")).
  settings(assemblySettings: _*).
  settings(
    name := "mal",
    version := "0.1",
    scalaVersion := "2.12.6",
    libraryDependencies ++= {
      Seq(
        "org.scalactic" %% "scalactic" % "3.0.5",
        "org.scalatest" %% "scalatest" % "3.0.5" % "test")
    }
  )
