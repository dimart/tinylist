package com.tinylist.client

import japgolly.scalajs.react.ReactComponentB
import japgolly.scalajs.react.vdom.all._
import japgolly.scalajs.react._

object App {
  val component = ReactComponentB[AppProps]("App")
      .initialState(AppState.default)
      .backend(new AppBackend(_))
      .renderPS {
        case (scope, props, state) =>
          div(`class` := "app")(
            // Top navigation bar
            nav(`class` := "navbar navbar-inverse navbar-fixed-top", role := "navigation",
              div(`class` := "container",
                div(`class` := "navbar-header",
                  a(`class` := "navbar-brand", href := "#",
                    "Tiny List"
                  )
                )
              )
            ),
            div(
              `class` := "container",

              p(
                `class` := "lead",
                "Create lists and share them!",
                p(
                  small(
                  "You can list movies from TMDB, songs from Spotify etc."
                  )
                )
              ),

              AddItemBar(state, scope.backend),
              ListView(state, scope.backend),
              SaveButton(state, scope.backend),
              if (state.tinyListId != props.tinyListId.getOrElse("X"))
                TinyListURLAlert(state, scope.backend)
              else
                div
            )
          )
      }
      .componentWillMount { current =>
        current.props.tinyListId match {
          case Some(tinyListId) => current.backend.fetch(tinyListId)
          case None => Callback(())
        }
      }
      .build

  def apply(props: AppProps) = component(props)
}