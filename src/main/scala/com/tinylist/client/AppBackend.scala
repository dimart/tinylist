package com.tinylist.client

import japgolly.scalajs.react._
import org.scalajs.dom.ext.KeyCode

import scala.concurrent.ExecutionContext.Implicits.global

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
      scope.modState(_.setUserInput(s))
    else
      scope.modState(_.setUserInput(s)) >>
      Callback.future(TMDBApi.searchMovie(s) map { movies => scope.modState(_.refreshCompletions(movies.map(_.title)))})
   }

  def mkListItem(e: ReactKeyboardEvent): Callback = {
    e.persist()
    scope.modState(s => {
      if (e.keyCode == KeyCode.Enter) {
        s.addListItem(TextItem(s.userInput)).setUserInput("")
      } else s
    })
  }
}
