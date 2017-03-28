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
          h1("Tiny List"),
          AddItemBar(),
          ListView(state, scope.backend)
        )
      }
      .build
}