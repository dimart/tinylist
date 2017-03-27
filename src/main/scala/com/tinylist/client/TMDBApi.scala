package com.tinylist.client

import scala.concurrent.ExecutionContext.Implicits.global
import org.scalajs.dom.ext.Ajax

import scala.scalajs.js
import scala.scalajs.js.{JSON, URIUtils}

import buildinfo.BuildInfo

@js.native
trait SearchResults extends js.Object {
  def results: js.Array[MovieInfo]
}

@js.native
trait MovieInfo extends js.Object {
  def title: String
  def overview: String
  def poster_path: String
}

object TMDBApi {
  private val apiKey = BuildInfo.TMDBApiKey

  def searchMovie(query: String) = {
    Ajax.get(movieSearchURL(query)) map { xhr =>
      JSON.parse(xhr.responseText).asInstanceOf[SearchResults].results.head.title
    }
  }

  def movieSearchURL(q: String) =
    s"https://api.themoviedb.org/3/search/movie?api_key=${apiKey}&query=${URIUtils.encodeURIComponent(q)}"
}
