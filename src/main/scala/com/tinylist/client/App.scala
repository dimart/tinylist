package com.tinylist.client

import japgolly.scalajs.react.ReactComponentB
import japgolly.scalajs.react.vdom.all._
import japgolly.scalajs.react._

object App {
  val component = ReactComponentB[Unit]("App")
      .render_P { _ =>
        div(`class` := "container")(
          h1("Tiny List"),
          p("Add items to your list")
        )
      }
//      .componentDidMount({ _ => Callback(
//
//      )})
      .build
}