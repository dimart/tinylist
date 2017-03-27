package com.tinylist.client

import japgolly.scalajs.react.ReactComponentB
import japgolly.scalajs.react.vdom.all._

object App {
  val component = ReactComponentB[Unit]("App")
      .render_P {
        case _ =>
          div(`class` := "container")(
            h1("Tiny List"),
            p(TMDBApi.searchMovie("King"))
          )
      }.build
}