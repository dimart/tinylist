package com.tinylist.client
import japgolly.scalajs.react._, extra.router._

sealed trait Page
case object Home extends Page

object Routing {
  val config = RouterConfigDsl[Page].buildConfig { dsl =>
    import dsl._
    (
      trimSlashes
        | staticRoute(root, Home) ~> renderR(renderAppDefault)
    ).notFound(redirectToPage(Home)(Redirect.Replace)).renderWith(layout)
  }

  def renderAppDefault(router: RouterCtl[Page]) = App.component()

  def layout(c: RouterCtl[Page], r: Resolution[Page]) = r.render()
}
