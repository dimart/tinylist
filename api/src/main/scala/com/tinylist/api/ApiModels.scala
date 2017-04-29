package com.tinylist.api

sealed trait ListItem
case class TextItem(text: String) extends ListItem
case class MovieItem(title: String, overview: String, posterURL: String) extends ListItem
case class TrackItem(
    trackName: String,
    artistName: String,
    album: String,
    previewURL: String,
    posterURL: String
) extends ListItem

case class TinyList(title: String, items: Seq[ListItem])
case class TinyListId(base64UUID: String)

object TinyLists {
  val default = TinyList("New List", Seq(TextItem("Sample text item")))
}