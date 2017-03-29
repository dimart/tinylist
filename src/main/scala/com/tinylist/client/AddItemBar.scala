package com.tinylist.client

import japgolly.scalajs.react.ReactComponentB
import japgolly.scalajs.react.vdom.all._
import japgolly.scalajs.react._

object AddItemBar {
  val component = ReactComponentB[(AppState, AppBackend)]("AddItemBar")
    .render_P {
      case (state, backend) =>
        div(`class` := "container",
          div(`class` := "row",
            div(`class` := "input-group",
              span(`class` := "input-group-btn",
                button(`class` := "btn btn-secondary", `type` := "button", "Add")
              ),
              input(`class` := "form-control", `type` :="text", placeholder := "Type here...",
                onChange ==> backend.editUserInput, onKeyUp ==> backend.userInputOnKeyUp,
                value := state.userInput)
            )
          ),
          div(`class` := "row",
          if (state.completions.nonEmpty)
            div(`class` := "list-group",
              state.completions.take(3) map {
                case t@TextItem(_) =>
                    button(
                      `type` := "button", `class` := "list-group-item list-group-item-action",
                      onClick --> backend.addListItem(t),
                      t.text,
                      span(`class` := "badge badge-default badge-pill", "Text"))
                case mi@MovieItem(t, o, _) =>
                  button(
                    `type` := "button", `class` := "list-group-item list-group-item-action flex-column align-items-start",
                    onClick --> backend.addListItem(mi),
                    h5(`class` := "mb-1", t),
                    p(`class` := "mb-1", o),
                    span(`class` := "badge badge-default badge-pill", "Movie"))
              }
            )
          else
            div
          )
        )
    }
    .build

  def apply(state: AppState, backend: AppBackend) = component((state, backend))
}
