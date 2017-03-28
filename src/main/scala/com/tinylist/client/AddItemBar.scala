package com.tinylist.client

import japgolly.scalajs.react.ReactComponentB
import japgolly.scalajs.react.vdom.all._
import japgolly.scalajs.react._

object AddItemBar {
  val component = ReactComponentB[(AppState, AppBackend)]("AddItemBar")
    .render_P {
      case (state, backend) =>
        div(`class` := "container",
          div(`class` := "input-group",
            span(`class` := "input-group-btn",
              button(`class` := "btn btn-secondary", `type` := "button", "Add")
            ),
            input(`class` := "form-control", `type` :="text", placeholder := "Type here...",
              onChange ==> backend.editUserInput, onKeyUp ==> backend.mkListItem,
              value := state.userInput)
          )
        )
    }
    .build

  def apply(state: AppState, backend: AppBackend) = component((state, backend))
}
