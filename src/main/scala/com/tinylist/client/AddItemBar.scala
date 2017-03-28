package com.tinylist.client

import japgolly.scalajs.react.ReactComponentB
import japgolly.scalajs.react.vdom.all._
import japgolly.scalajs.react._

object AddItemBar {
  val component = ReactComponentB[Unit]("AddItemBar")
      .render_S(_ => {
        div(`class` := "container",
          p("Add elements to your list here")
        )
      })
      .build

  def apply() = component()
}
