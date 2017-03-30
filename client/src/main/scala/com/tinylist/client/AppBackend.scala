package com.tinylist
package client

import api._
import autowire._
import japgolly.scalajs.react._
import org.scalajs.dom.ext.KeyCode

import scala.concurrent.ExecutionContext.Implicits.global
import upickle.default.{read => uread}

class AppBackend(scope: BackendScope[Unit, AppState]) {
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
      }})
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
          scope.modState(_.setTinyListId(id.base64UUID)))
      )
    )
  }
}
