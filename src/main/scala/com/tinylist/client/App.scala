package com.tinylist.client

import japgolly.scalajs.react.ReactComponentB
import japgolly.scalajs.react.vdom.all._
import japgolly.scalajs.react._

object App {
  val component = ReactComponentB[Unit]("App")
      .initialState(AppState.default)
      .backend(new AppBackend(_))
      .renderPS { case (scope, _, state) =>
        div(`class` := "app")(
          // Top navigation bar
          nav(`class` := "navbar navbar-inverse navbar-fixed-top", role := "navigation",
            div(`class` := "container",
              div(`class` := "navbar-header",
                a(`class` := "navbar-brand", href := "#",
                  "Tiny List"
                )
              )
            )
          ),
          AddItemBar(),
          ListView(state, scope.backend)
        )
      }
      .build
}