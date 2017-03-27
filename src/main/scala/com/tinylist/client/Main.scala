package com.tinylist.client

import japgolly.scalajs.react.ReactDOM
import org.scalajs.dom

import scala.scalajs.js.JSApp

object Main extends JSApp {
  def main(): Unit = {
    val container = dom.document.createElement("div").asInstanceOf[dom.raw.HTMLDivElement]
    container.className = "root"
    dom.document.body.appendChild(container)

    ReactDOM.render(App.component(), container)
  }
}