package com.tinylist.client

import japgolly.scalajs.react.ReactComponentB
import japgolly.scalajs.react.vdom.all._
import org.scalajs.dom

object TinyListURLAlert {
  val component = ReactComponentB[(AppState, AppBackend)]("TinyListURLNotification")
    .render_P {
      case (state, backend) =>
        if (state.tinyListId.base64UUID.isEmpty) div
        else {
          div(
            `role` := "alert",
            `class` := "footer alert alert-success",
            strong("Saved!"),
            p(
              a(
                href := state.tinyListId.base64UUID,
                `class` := "alert-link",
                s" ${dom.window.location.hostname}/${state.tinyListId.base64UUID}"
              )
            )
          )
        }
    }
    .build

  def apply(state: AppState, backend: AppBackend) = component((state, backend))
}
