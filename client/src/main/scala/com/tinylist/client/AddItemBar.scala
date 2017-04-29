package com.tinylist
package client

import api._
import japgolly.scalajs.react.ReactComponentB
import japgolly.scalajs.react.vdom.all._
import japgolly.scalajs.react._

object AddItemBar {
  val component = ReactComponentB[(AppState, AppBackend)]("AddItemBar")
    .render_P {
      case (state, backend) =>
        div(
          `class` := "dropdown autocomplete",

          input(
            `class` := "dropdown-toggle",
            `type` :="text",
            placeholder := "Add list item here...",
            "data-toggle".reactAttr := "dropdown",
            onChange ==> backend.editUserInput,
            onKeyUp ==> backend.userInputOnKeyUp,
            value := state.userInput
          ),

          if (state.completions.nonEmpty)
            ul(`class` := "dropdown-menu dialog open", role := "menu",
              state.completions.take(3) map {
                case t@TextItem(_) =>
                  li(
                      onClick --> backend.addListItem(t),
                      span(`class` := "badge badge-default badge-pill", "Text")
                  )
                case mi@MovieItem(t, _, _) =>
                  li(
                    onClick --> backend.addListItem(mi),
                    a(
                      div(`class` := "row",
                        div(`class` := "col-xs-6",
                          t
                        ),
                        div(`class` := "col-xs-6 text-right",
                          span(`class` := "badge badge-default badge-pill", "Movie")
                        )
                      )
                    )
                  )
              }
            )
            else
              div
        )
    }
    .build

  def apply(state: AppState, backend: AppBackend) = component((state, backend))
}
