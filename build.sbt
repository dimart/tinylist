import sbt.Keys._

val TMDBApiKey = sys.props.getOrElse("TMDBApiKey", default = "DEFINE YOUR API KEY HERE")

val orgSettings = Seq(
  organization := "org.tinylist",
  version := "0.1"
)

lazy val baseSettings = Seq(
  scalaVersion := "2.11.8"
) ++ orgSettings

lazy val logging =
  libraryDependencies ++= Seq(
    "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0",
    "com.typesafe.akka" %% "akka-slf4j" % "2.4.16",
    "ch.qos.logback" % "logback-classic" % "1.2.1"
  )

lazy val client = project
  .settings(baseSettings)
  .settings(
    persistLauncher in Compile := true,
    libraryDependencies ++= Seq(
      "com.github.japgolly.scalajs-react" %%% "core" % "0.11.3",
      "org.querki" %%% "jquery-facade" % "1.0",
      "org.querki" %%% "querki-jsext" % "0.8"
    ),
    jsDependencies ++= Seq(
      "org.webjars.bower" % "react" % "15.3.2" / "react-with-addons.js" minified "react-with-addons.min.js" commonJSName "React",
      "org.webjars.bower" % "react" % "15.3.2" / "react-dom.js" minified "react-dom.min.js" dependsOn "react-with-addons.js" commonJSName "ReactDOM",
      "org.webjars.bower" % "react" % "15.3.2" / "react-dom-server.js" minified  "react-dom-server.min.js" dependsOn "react-dom.js" commonJSName "ReactDOMServer",

      "org.webjars" % "jquery" % "2.2.1" / "jquery.js" minified "jquery.min.js",
      "org.webjars" % "jquery-ui" % "1.12.1" / "jquery-ui.js" dependsOn "jquery.js"
    ),
    buildInfoKeys := Seq[BuildInfoKey]("TMDBApiKey" -> TMDBApiKey)
  )
  .enablePlugins(ScalaJSPlugin, BuildInfoPlugin)

lazy val server = project
  .settings(baseSettings)
  .settings(logging)
  .settings(
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http" % "10.0.5"
    )
  )

lazy val tinylist = project
  .in(file("."))
  .aggregate(
    client,
    server
  )
  .settings(orgSettings)