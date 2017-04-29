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
  def editTitle(): Callback = {
    scope.modState(_.setIsEditingTitle(true))
  }

  def editTitle(e: ReactEventI): Callback = {
    e.persist()
    scope.modState(_.setTitle(e.target.value))
  }

  def saveTitle(e: ReactKeyboardEvent): Callback = {
    e.persist()
    scope.modState(_.setIsEditingTitle(e.keyCode != KeyCode.Enter))
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
          results => {
            scope.modState(_.refreshCompletions(results map {
              res => {
                MovieItem(title = res.title, overview = res.overview, posterURL = TMDBApi.moviePosterURL(res.poster_path))
              }
            }))
          }
        }
      ) >>
      Callback.future(
        SpotifyApi.searchTrack(s) map {
          results => {
            scope.modState(_.refreshCompletions(results map {
              res => {
                TrackItem(name = res.name, album = res.album.name , previewURL = res.preview_url, posterURL = res.album.images(0).url)
              }
            }))
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
