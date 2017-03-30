package com.tinylist.client

import japgolly.scalajs.react.ReactComponentB
import japgolly.scalajs.react.vdom.all._
import org.scalajs.dom

object TinyListURLAlert {
  val component = ReactComponentB[(AppState, AppBackend)]("TinyListURLNotification")
    .render_P {
      case (state, backend) =>
        if (state.tinyListId.isEmpty) div
        else {
          div(
            `role` := "alert",
            `class` := "alert alert-success",
            strong("Saved!"),
            a(href := state.tinyListId, `class` := "alert-link", s" ${dom.window.location.hostname}/${state.tinyListId}")
          )
        }
    }
    .build

  def apply(state: AppState, backend: AppBackend) = component((state, backend))
}
