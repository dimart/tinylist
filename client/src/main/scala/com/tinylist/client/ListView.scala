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

        div(
          if (state.isEditingTitle) renderEditing() else renderTitle(),
          div(
            state.tinyList.items map {
              case ti @ TextItem(t) =>
                div(
                  `class` := "media panel panel-default",
                  div(
                    `class` := "media-body panel-body",
                    t
                  ),

                  div(
                    `class` := "media-right",
                    p(
                      a(
                        `class` := "badge btn",
                        onClick --> backend.removeListItem(ti),
                        "Remove"
                      )
                    )
                  )
                )
              case mi @ MovieItem(t, o, posterURL) =>
                div(
                  `class` := "media panel panel-default",
                  div(
                    `class` := "media-left",
                    img(
                      `class` := "media-object",
                      src := posterURL
                    )
                  ),
                  div(
                    `class` := "media-body panel-body",
                    h5(`class` := "media-heading", t),
                    p(o)
                  ),

                  div(
                    `class` := "media-right",
                    p(
                      a(
                        `class` := "badge btn",
                        onClick --> backend.removeListItem(mi),
                        "Remove"
                      )
                    )
                  )
                )
            }
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
