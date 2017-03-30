package com.tinylist.server.routes

import akka.http.scaladsl.server.Directives._
import com.tinylist.api._
import com.tinylist.server.{AutowireApiImplementation, AutowireServer}
import upickle.default.{read => uread}
import scala.concurrent.ExecutionContext.Implicits.global

class AutowireApiRoutes(api: AutowireApiImplementation) {
  val routes =
    post(
      path("api" / Segments)(
        s ⇒
          entity(as[String])(
            e ⇒
              complete {
                AutowireServer.route[AutowireApi](api)(
                    autowire.Core.Request(s, uread[Map[String, String]](e)))
              }
          )
        )
    )
}
