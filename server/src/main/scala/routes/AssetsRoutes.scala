package routes

import akka.http.scaladsl.server.Directives._

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
