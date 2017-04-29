package com.tinylist.client.api

import org.scalajs.dom.ext.Ajax

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.scalajs.js
import scala.scalajs.js.{JSON, URIUtils}

@js.native
trait TMDBSearchResults extends js.Object {
  def results: js.Array[MovieInfo]
}

@js.native
trait MovieInfo extends js.Object {
  def title: String
  def overview: String
  def poster_path: String
}

object TMDBApi {
  private val apiKey = buildinfo.BuildInfo.TMDBApiKey

  def searchMovie(query: String): Future[Seq[MovieInfo]] = {
    Ajax.get(movieSearchURL(query)) map { xhr =>
      JSON.parse(xhr.responseText).asInstanceOf[TMDBSearchResults].results
    }
  }

  def moviePosterURL(path: String) =
    s"https://image.tmdb.org/t/p/w185_and_h278_bestv2/${path}"

  private def movieSearchURL(q: String) =
    s"https://api.themoviedb.org/3/search/movie?api_key=${apiKey}&query=${URIUtils.encodeURIComponent(q)}"
}
