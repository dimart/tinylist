package com.tinylist.server.routes

import akka.http.scaladsl.server.Directives._

/**
  * Programmed by dmitriipetukhov on 3/30/17.
  */
object PublicRoutes {
  val routes =
    concat(
      get(
        concat(
          pathSingleSlash(index),
          path(Segment ~ Slash.?)((_) => index)
        )
      ),
      AssetsRoutes.routes
    )

  val index = getFromResource("public/views/index.html")
}
