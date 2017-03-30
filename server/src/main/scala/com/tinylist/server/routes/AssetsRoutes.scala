package com.tinylist.server.routes

import akka.http.scaladsl.server.Directives._

/**
  * Programmed by dmitriipetukhov on 3/30/17.
  */
object AssetsRoutes {
  val routes =
    get(
      concat(
        path("assets" / "public" / "css" / Remaining)(
          path ⇒ getFromResource("public/css" + path)
        ),
        path("assets" / "public" / Remaining)(
          path ⇒ getFromResource("public/" + path)
        )
      )
    )
}
