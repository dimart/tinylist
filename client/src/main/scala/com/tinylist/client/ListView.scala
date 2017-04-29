package com.tinylist
package client

import com.tinylist.api._
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

        def mkListItem(listItem: ListItem, left: ReactTag, body: ReactTag) = {
          div(
            `class` := "media panel panel-default",
            div(
              `class` := "media-left",
              left
            ),
            div(
              `class` := "media-body panel-body",
              body
            ),

            div(
              `class` := "media-right",
              span(
                `class` := "remove",
                onClick --> backend.removeListItem(listItem),
                "Remove"
              )
            )
          )
        }

        div(
          if (state.isEditingTitle) renderEditing() else renderTitle(),
          div(
            state.tinyList.items map {
              case ti @ TextItem(t) => mkListItem(ti, left = div, body = div(t))
              case mi @ MovieItem(t, o, posterURL) => {
                mkListItem(
                  mi,
                  left = img(
                    `class` := "media-object icon",
                    src := posterURL
                  ),
                  body = div(
                    h5(`class` := "media-heading", t),
                    p(o)
                  )
                )
              }

              case ti @ TrackItem(trackName, artistName, album, previewURL, posterURL) => {
                mkListItem(
                  ti,
                  left = img(
                    `class` := "media-object icon",
                    src := posterURL
                  ),
                  body = div(
                    h5(`class` := "media-heading", artistName),
                    p(trackName + " from \"" + album + "\""),
                    audio(
                      controls := true,
                      key := previewURL,
                      source(
                        src := previewURL
                      )
                    )
                  )
                )
              }
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
