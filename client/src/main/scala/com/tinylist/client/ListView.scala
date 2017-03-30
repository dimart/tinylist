package com.tinylist
package client

import api._

import japgolly.scalajs.react.ReactComponentB
import japgolly.scalajs.react.vdom.all._

object ListView {
  val component = ReactComponentB[(AppState, AppBackend)]("ListView")
    .render_P {
      case (state, backend) =>
        def renderTitle() = {
          h4(s"${state.tinyList.title}", onDoubleClick --> backend.editTitle)
        }

        def renderEditing() = {
          input(`type` := "text", `value` := state.tinyList.title, `class` := "form-control",
            onChange ==> backend.editTitle, onKeyUp ==> backend.saveTitle)
        }

        div(`class` := "container",
          div(`class` := "row",
            div(`class` := "col-lg-12 text-start",
              if (state.isEditingTitle) renderEditing() else renderTitle()
            )
          ),
          div(`class` := "row",
            div(`class` := "card",
            ul(`class` := "list-group list-group-flush",
              state.tinyList.items map {
                case ti @ TextItem(t) =>
                  button(`type` := "button", `class` := "list-group-item", onClick --> backend.removeListItem(ti), t)
                case mi @ MovieItem(t, o, posterURL) =>
                  button(
                      `type` := "button", `class` := "list-group-item list-group-item-action flex-column align-items-start",
                      onClick --> backend.removeListItem(mi),
                      img(`class` := "mb-1", src := posterURL),
                      h5(`class` := "mb-1", t),
                      p(`class` := "mb-1", o),
                      span(`class` := "badge badge-default badge-pill", "Movie")
                  )
              }
            )
          )
          )
        )
    }
    .shouldComponentUpdate { v =>
      val (nextState, _) = v.nextProps
      val (currState, _) = v.currentProps
      !nextState.equals(currState)
    }
    .build

  def apply(state: AppState, backend: AppBackend) = component((state, backend))
}
