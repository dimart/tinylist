package com.tinylist
package client

import api._

object AppState {
  def default = AppState(
    userInput = "",
    completions = Seq(),
    tinyList = TinyList("New List", Seq(TextItem("Tap me to delete"))),
    tinyListId = "",
    isEditingTitle = false
  )
}

case class AppState(
                     userInput: String,
                     completions: Seq[ListItem],
                     tinyList: TinyList,
                     tinyListId: String,
                     isEditingTitle: Boolean
) {

  def setUserInput(v: String): AppState = {
    copy(userInput = v)
  }

  def setIsEditingTitle(v: Boolean): AppState = {
    copy(isEditingTitle = v)
  }

  def setTitle(v: String): AppState = {
    copy(tinyList = TinyList(v, this.tinyList.items))
  }

  def addListItem(v: ListItem): AppState = {
    val tl = this.tinyList
    copy(tinyList = TinyList(tl.title, tl.items :+ v))
  }

  def removeListItem(v: ListItem): AppState = {
    val tl = this.tinyList
    copy(tinyList = TinyList(tl.title, tl.items.filter(!_.equals(v))))
  }

  def refreshCompletions(v: Seq[ListItem]): AppState = {
    copy(completions = v)
  }

  def setTinyListId(v: String): AppState = {
    copy(tinyListId = v)
  }
}
