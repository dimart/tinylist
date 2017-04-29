package com.tinylist
package client

import com.tinylist.api._
import japgolly.scalajs.react.ReactComponentB
import japgolly.scalajs.react.vdom.all._
import japgolly.scalajs.react._

object AddItemBar {
  val component = ReactComponentB[(AppState, AppBackend)]("AddItemBar")
    .render_P {
      case (state, backend) =>

        def mkCompletionListItem(listItem: ListItem, text: String, badge: String) = {
          li(
            onClick --> backend.addListItem(listItem),
            a(
              div(`class` := "row",
                div(`class` := "col-xs-6",
                  text
                ),
                div(`class` := "col-xs-6 text-right",
                  span(`class` := "badge badge-default badge-pill", badge)
                )
              )
            )
          )
        }

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
              state.completions.take(4) map {
                case t@TextItem(text) => mkCompletionListItem(t, text, "Text")
                case mi@MovieItem(title, _, _) => mkCompletionListItem(mi, title, "Movie")
                case ti@TrackItem(name, album, _, _) => mkCompletionListItem(ti, name + " from " + album, "Track")
              }
            )
            else
              div
        )
    }
    .build

  def apply(state: AppState, backend: AppBackend) = component((state, backend))
}
