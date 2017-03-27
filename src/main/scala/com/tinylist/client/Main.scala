package com.tinylist.client

import japgolly.scalajs.react.ReactDOM
import japgolly.scalajs.react.vdom.all._
import org.scalajs.dom

import scala.concurrent.ExecutionContext.Implicits.global
import org.querki.jquery._
import com.tinylist.client.facades.AutocompleteFacade._
import com.tinylist.client.facades.{AutocompleteOptions, JQueryUIAutocomplete}

import scala.scalajs.js.JSApp

object Main extends JSApp {
  def main(): Unit = {
    val container = dom.document.createElement("div").asInstanceOf[dom.raw.HTMLDivElement]
    container.className = "root"
    dom.document.body.appendChild(container)

    ReactDOM.render(App.component(), container)

    val search = dom.document.createElement("input").asInstanceOf[dom.raw.HTMLDivElement]
    search.id = "search"
    dom.document.body.appendChild(search)
    $(search).autocomplete(AutocompleteOptions.source(List("apple", "ananas", "banana")))
  }
}