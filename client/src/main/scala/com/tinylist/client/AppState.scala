package com.tinylist.client

abstract class ListItem()
case class TextItem(text: String) extends ListItem
case class MovieItem(title: String, overview: String, posterURL: String) extends ListItem

object AppState {
  def default = AppState(
    userInput = "",
    completions = Seq(),
    title = "New List",
    isEditingTitle = false,
    items = Seq(TextItem("Tap me to delete"))
  )
}

case class AppState(
     userInput: String,
     completions: Seq[ListItem],
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

  def removeListItem(v: ListItem): AppState = {
    copy(items = items.filter(!_.equals(v)))
  }

  def refreshCompletions(v: Seq[ListItem]): AppState = {
    copy(completions = v)
  }
}
