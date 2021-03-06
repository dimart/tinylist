package com.tinylist
package client

import autowire._
import com.tinylist.api._
import com.tinylist.client.api.{SpotifyApi, TMDBApi}
import japgolly.scalajs.react._
import org.scalajs.dom.ext.KeyCode
import upickle.default.{read => uread}

import scala.concurrent.ExecutionContext.Implicits.global

class AppBackend(scope: BackendScope[AppProps, AppState]) {

  def editTitle(e: ReactEventI): Callback = {
    e.persist()
    scope.modState(_.setTitle(e.target.value))
  }

  def toggleEditingMode(): Callback = {
    scope.modState({x => {
      if (x.tinyList.title != "")
        x.setIsEditingTitle(!x.isEditingTitle)
      else
        x.setTitle("New List").setIsEditingTitle(false)
    }})
  }

  def editUserInput(e: ReactEventI): Callback = {
    e.persist()
    val s = e.target.value
    if (s.length < 3)
      scope.modState(_.setUserInput(s).refreshCompletions(Seq()))
    else
      scope.modState(_.setUserInput(s)) >>
      Callback.future(
        TMDBApi.searchMovie(s) map {
        TMDBResults => {
          Callback.future(
            SpotifyApi.searchTrack(s) map {
              SpotifyResults => {
                scope.modState(_.refreshCompletions(
                  (TMDBResults map {
                    x => {
                      MovieItem(
                        title = x.title,
                        overview = x.overview,
                        posterURL = TMDBApi.moviePosterURL(x.poster_path)
                      )
                    }
                  }) ++ (SpotifyResults map {
                    x => {
                      TrackItem(
                        trackName = x.name,
                        artistName = x.artists(0).name,
                        album = x.album.name ,
                        previewURL = x.preview_url,
                        posterURL = x.album.images(0).url
                      )
                    }
                  })
                ))
              }
            }
          )
        }
      }
    )
   }

  def userInputOnKeyUp(e: ReactKeyboardEvent): Callback = {
    e.persist()
    scope.modState(s => {
      if (e.keyCode == KeyCode.Enter) {
        s.addListItem(TextItem(s.userInput)).setUserInput("").refreshCompletions(Seq())
      } else s
    })
  }

  def addListItem(li: ListItem): Callback = {
    scope.modState(s => s.addListItem(li).setUserInput("").refreshCompletions(Seq()))
  }

  def removeListItem(li: ListItem): Callback = {
    scope.modState(s => s.removeListItem(li))
  }

  def save(): Callback = {
    scope.state.flatMap(state =>
      Callback.future(
        ApiClient[AutowireApi].save(state.tinyList).call().map(id =>
          scope.modState(_.setTinyListId(id)))
      )
    )
  }

  def fetch(tinyListId: TinyListId): Callback = {
    scope.state.flatMap(state =>
      Callback.future(
        ApiClient[AutowireApi].fetch(tinyListId).call().map(tl => {
          if (tl == TinyLists.default) goHome() else {
            scope.modState(_.setTinyList(tl)) >> scope.modState(_.setTinyListId(tinyListId))
          }
        })
      )
    )
  }

  def goHome(): Callback = {
    scope.props.flatMap(
      props => props.router.map(_.set(Home)).getOrElse(Callback(()))
    ) >> scope.modState(_.setTinyListId(TinyListId(""))) >> scope.modState(_.setTinyList(TinyLists.default))
  }
}
