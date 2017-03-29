package routes

import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpResponse}
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives._

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
