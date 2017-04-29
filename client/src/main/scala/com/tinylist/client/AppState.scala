package com.tinylist
package client

import com.tinylist.api._

object AppState {
  def default = AppState(
    userInput = "",
    completions = Seq(),
    tinyList = TinyLists.default,
    tinyListId = TinyListId(""),
    isEditingTitle = false
  )
}

case class AppState(
                     userInput: String,
                     completions: Seq[ListItem],
                     tinyList: TinyList,
                     tinyListId: TinyListId,
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
    val movies = v filter {case mi:MovieItem => true; case _ => false}
    val tracks = v filter {case ti:TrackItem => true; case _ => false}
    copy(completions = movies.take(2) union tracks.take(2))
  }

  def setTinyListId(v: TinyListId): AppState = {
    copy(tinyListId = v)
  }

  def setTinyList(v: TinyList): AppState = {
    copy(tinyList = v)
  }
}
