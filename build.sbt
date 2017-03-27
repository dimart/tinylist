lazy val root = (project in file("."))
  .settings(
    name := "tinylist",
    scalaVersion := "2.11.8",
    persistLauncher in Compile := true
  )
  .enablePlugins(ScalaJSPlugin)
