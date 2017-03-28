package com.tinylist.client

import japgolly.scalajs.react._
import org.scalajs.dom.ext.KeyCode

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
}
