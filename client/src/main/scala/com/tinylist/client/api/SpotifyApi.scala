package com.tinylist.client.api

import org.scalajs.dom.ext.Ajax

import scala.concurrent.Future
import scala.scalajs.js
import scala.scalajs.js.{JSON, URIUtils}

import scala.concurrent.ExecutionContext.Implicits.global

@js.native
trait SpotifySearchResults extends js.Object {
  def tracks: ItemListing[TrackInfo]
}

@js.native
trait ItemListing[T] extends js.Object {
  def items: js.Array[T]
}

@js.native
trait TrackInfo extends js.Object {
  def album: AlbumInfo
  def artists: js.Array[ArtistInfo]
  def name: String
  def track_number: Int
  def duration_ms: Int
  def preview_url: String
}

@js.native
trait AlbumInfo extends js.Object {
  def name: String
  def images: js.Array[AlbumCover]
}

@js.native
trait ArtistInfo extends js.Object {
  def name: String
}

@js.native
trait AlbumCover extends js.Object {
  def height: String
  def width: String
  def url: String
}

object SpotifyApi {

  def searchTrack(query: String): Future[Seq[TrackInfo]] = {
    Ajax.get(tracksSearchURL(query)) map { xhr =>
      JSON.parse(xhr.responseText).asInstanceOf[SpotifySearchResults].tracks.items
    }
  }

  private def tracksSearchURL(q: String) = {
    s"https://api.spotify.com/v1/search?q=${URIUtils.encodeURIComponent(q)}&type=track"
  }
}
