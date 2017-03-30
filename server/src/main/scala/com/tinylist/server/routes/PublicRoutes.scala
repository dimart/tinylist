package com.tinylist.server.routes

import akka.http.scaladsl.server.Directives._

/**
  * Programmed by dmitriipetukhov on 3/30/17.
  */
object PublicRoutes {
  val routes =
    concat(
      get(
        pathSingleSlash(
          getFromResource("public/views/index.html")
        )
      ),
      AssetsRoutes.routes
    )
}
