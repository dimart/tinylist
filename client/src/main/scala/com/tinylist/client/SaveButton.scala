package com.tinylist.client

import japgolly.scalajs.react.ReactComponentB
import japgolly.scalajs.react.vdom.all._

object SaveButton {
  val component = ReactComponentB[(AppState, AppBackend)]("SaveButton")
    .render_P {
      case (state, backend) =>
        button(
          `type` := "button",
          `class` := "btn btn-info", onClick --> backend.save,
          "Save List"
        )
    }
    .build

  def apply(state: AppState, backend: AppBackend) = component((state, backend))
}
