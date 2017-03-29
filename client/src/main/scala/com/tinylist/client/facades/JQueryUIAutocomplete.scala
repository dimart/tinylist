package com.tinylist.client.facades

import org.querki.jquery.JQuery
import org.querki.jsext._

import scala.scalajs.js
import js.JSConverters._

@js.native
trait JQueryUIAutocomplete extends JQuery {
  def autocomplete(options: AutocompleteOptions): JQuery = js.native
}

@js.native
trait AutocompleteOptions extends js.Object
object AutocompleteOptions extends AutocompleteOptionBuilder(noOpts) // companion object ~ empty AutocompleteOptions
class AutocompleteOptionBuilder(val dict:OptMap)
  extends JSOptionBuilder[AutocompleteOptions, AutocompleteOptionBuilder](new AutocompleteOptionBuilder(_)) {

  // Which element the menu should be appended to
  def appendTo(x: String) = jsOpt("appendTo", x)

  // If set to true the first item will automatically be focused when the menu is shown
  def autoFocus(x: Boolean) = jsOpt("autoFocus", x)

  // Specify additional classes to add to the widget's elements.
  def classes(x: js.Object) = jsOpt("classes", x)

  // The delay in milliseconds between when a keystroke occurs and when a search is performed.
  def delay(x: Integer) = jsOpt("delay", x)

  // Disables the autocomplete if set to true.
  def disabled(x: Boolean) = jsOpt("disabled", x)

  // The minimum number of characters a user must type before a search is performed.
  def minLength(x: Integer) = jsOpt("minLength", x)

  // Identifies the position of the suggestions menu in relation to the associated input element.
  def position(x: js.Object) = jsOpt("position", x)

  def source(x: Seq[String]) = jsOpt("source", x.toJSArray)
  def source(x: String) = jsOpt("source", x)
}

object AutocompleteFacade {
  implicit def jq2jqUI(jq: JQuery): JQueryUIAutocomplete = jq.asInstanceOf[JQueryUIAutocomplete]
}