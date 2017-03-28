package com.tinylist.client

object AppState {
  def default = AppState(
    title = "New List",
    isEditingTitle = false
  )
}

case class AppState(
     title: String,
     isEditingTitle: Boolean
) {
  def setIsEditingTitle(v: Boolean): AppState = {
    copy(isEditingTitle = v)
  }

  def setTitle(v: String): AppState = {
    copy(title = v)
  }
}
