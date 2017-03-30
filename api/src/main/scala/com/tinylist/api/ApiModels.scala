package com.tinylist.api

abstract class ListItem()
case class TextItem(text: String) extends ListItem
case class MovieItem(title: String, overview: String, posterURL: String) extends ListItem

case class TinyList(title: String, items: Seq[ListItem])
case class TinyListId(base64UUID: String)
