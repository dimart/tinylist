package com.tinylist.client

object AppState {
  def default = AppState(
    userInput = "",
    title = "New List",
    isEditingTitle = false,
    items = Seq(TextItem("Finish proposal"), TextItem("Eat an apple"))
  )
}

abstract class ListItem()
case class TextItem(text: String) extends ListItem
case class MovieItem(title: String, overview: String) extends ListItem

case class AppState(
     userInput: String,
     title: String,
     isEditingTitle: Boolean,
     items: Seq[ListItem]
) {

  def setUserInput(v: String): AppState = {
    copy(userInput = v)
  }

  def setIsEditingTitle(v: Boolean): AppState = {
    copy(isEditingTitle = v)
  }

  def setTitle(v: String): AppState = {
    copy(title = v)
  }

  def addListItem(v: ListItem): AppState = {
    copy(items = items :+ v)
  }
}
