package com.tinylist.server.routes

import akka.http.scaladsl.server.Directives._
import com.tinylist.api._
import com.tinylist.server.{AutowireApiImplementation, AutowireServer}
import scala.concurrent.ExecutionContext.Implicits.global
import upickle.default.{read => uread}

object AutowireApiRoutes {
  val routes =
    post(
      path("api" / Segments)(
        s ⇒
          entity(as[String])(
            e ⇒
              complete {
                val api = new AutowireApiImplementation
                AutowireServer.route[AutowireApi](api)(
                    autowire.Core.Request(s, uread[Map[String, String]](e)))
              }
          )
        )
    )
}
