package com.tinylist.client

import buildinfo.BuildInfo

object TMDBApi {
  private val apiKey = BuildInfo.TMDBApiKey

  def searchMovie(query: String) = {
    apiKey
  }
}
