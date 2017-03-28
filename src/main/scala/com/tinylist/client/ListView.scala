package com.tinylist.client

import japgolly.scalajs.react.ReactComponentB
import japgolly.scalajs.react.vdom.all._

object ListView {
  val component = ReactComponentB[(AppState, AppBackend)]("ListView")
    .render_P {
      case (state, backend) =>
        println("rendered")

        def renderTitle() = {
          p(s"${state.title}", onDoubleClick --> backend.editTitle)
        }

        def renderEditing() = {
          input(`type` := "text", `value` := state.title, `class` := "form-control",
            onChange ==> backend.editTitle, onKeyUp ==> backend.saveTitle)
        }

        div(`class` := "container",
          div(`class` := "row",
            div(`class` := "col-lg-12 text-center",
              if (state.isEditingTitle) renderEditing() else renderTitle()
            )
          ),
          div(`class` := "row",
            div(`class` := "card",
            ul(`class` := "list-group list-group-flush",
              state.items map {
                case TextItem(t) => li(`class` := "list-group-item", t)
              },
              // DEBUG ONLY
              state.completions map { s => li(`class` := "list-group-item", s) }
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
