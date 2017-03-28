package com.tinylist.client

import japgolly.scalajs.react.ReactComponentB
import japgolly.scalajs.react.vdom.all._

object ListView {
  val component = ReactComponentB[(AppState, AppBackend)]("ListView")
    .render_P {
      case (state, backend) =>
        println("rendered")

        def renderTitle() = {
          div(`class` := "col-xs-3",
            p(s"${state.title}", onDoubleClick --> backend.editTitle)
          )
        }

        def renderEditing() = {
          div(`class` := "col-xs-3",
            input(`type` := "text", `value` := state.title, `class` := "form-control",
              onChange ==> backend.editTitle, onKeyUp ==> backend.saveTitle)
          )
        }

        if (state.isEditingTitle) renderEditing() else renderTitle()
    }
    .shouldComponentUpdate { v =>
      val (nextState, _) = v.nextProps
      val (currState, _) = v.currentProps
      !nextState.equals(currState)
    }
    .build

  def apply(state: AppState, backend: AppBackend) = component((state, backend))
}
