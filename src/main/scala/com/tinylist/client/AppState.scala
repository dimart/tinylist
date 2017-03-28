package com.tinylist.client

object AppState {
  def default = AppState(
    title = "New List",
    isEditingTitle = false,
    items = Seq()
  )
}

case class ListItem()
case class TextItem(text: String) extends ListItem
case class MovieItem(title: String, overview: String) extends ListItem

case class AppState(
     title: String,
     isEditingTitle: Boolean,
     items: Seq[ListItem]
) {
  def setIsEditingTitle(v: Boolean): AppState = {
    copy(isEditingTitle = v)
  }

  def setTitle(v: String): AppState = {
    copy(title = v)
  }
}
