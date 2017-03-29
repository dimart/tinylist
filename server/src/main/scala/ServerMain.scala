import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.typesafe.scalalogging.Logger
import routes.PublicRoutes

import scala.concurrent.Await
import scala.concurrent.duration._

object ServerMain {
  def main(args: Array[String]): Unit = {
    val logger = Logger("ServerMain")

    implicit val system = ActorSystem("tinylist")
    implicit val materializer = ActorMaterializer()

    Await.result(Http().bindAndHandle(PublicRoutes.routes, "0.0.0.0", 8080), 1.seconds)
    logger.info(s"Server online at localhost:8080")

    Await.result(system.whenTerminated, Duration.Inf)
  }
}
