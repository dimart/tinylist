import akka.actor.ActorSystem
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import akka.http.scaladsl.Http

import scala.concurrent.duration._
import scala.concurrent.Await

import com.typesafe.scalalogging.Logger

object ServerMain {
  def main(args: Array[String]): Unit = {
    val logger = Logger("ServerMain")

    implicit val system = ActorSystem("tinylist")
    implicit val materializer = ActorMaterializer()
    import system.dispatcher

    val route =
      path("hello") {
        get {
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
        }
      }

    Await.result(Http().bindAndHandle(route, "0.0.0.0", 8080), 1.seconds)
    logger.info(s"Server online at http://localhost:8080")

    Await.result(system.whenTerminated, Duration.Inf)
  }
}
