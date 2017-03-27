val TMDBApiKey = sys.props.getOrElse("TMDBApiKey", default = "DEFINE YOUR API KEY HERE")

lazy val root = (project in file("."))
  .enablePlugins(BuildInfoPlugin)
  .settings(
    name := "tinylist",
    scalaVersion := "2.11.8",
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
  .enablePlugins(ScalaJSPlugin)
