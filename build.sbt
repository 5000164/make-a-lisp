import sbtassembly.AssemblyPlugin.defaultShellScript

lazy val assemblySettings = Seq(
  test in assembly := {},
  assemblyJarName in assembly := "mal.jar",
  assemblyOption in assembly := (assemblyOption in assembly).value.copy(prependShellScript = Some(defaultShellScript))
)

lazy val root = (project in file("."))
  .settings(assemblySettings: _*)
  .settings(
    name := "make-a-lisp",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := "2.12.8",
    libraryDependencies ++= {
      Seq("org.scalatest" %% "scalatest" % "3.0.5" % "test", "org.scalactic" %% "scalactic" % "3.0.5" % "test")
    }
  )
