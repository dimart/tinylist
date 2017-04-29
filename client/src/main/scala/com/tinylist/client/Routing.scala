package com.tinylist
package client

import com.tinylist.api._
import japgolly.scalajs.react._
import extra.router._

sealed trait Page
case object Home extends Page

sealed trait ResourcePage extends Page
case class TinyListIdPage(uuid: String) extends ResourcePage

object Routing {
  val config = RouterConfigDsl[Page].buildConfig { dsl =>
    import dsl._

    val alpha = string("[a-zA-Z0-9]*")

    (
      trimSlashes
        | staticRoute(root, Home) ~> renderR(renderAppDefault)
        | dynamicRouteCT(alpha.caseClass[TinyListIdPage]) ~> dynRenderR(renderPage)
    ).notFound(redirectToPage(Home)(Redirect.Replace)).renderWith(layout)
  }

  def renderAppDefault(router: RouterCtl[Page]) =
    App.component(
      AppProps(
        router = Some(router),
        tinyListId = None
      )
    )

  def renderPage(page: ResourcePage, router: RouterCtl[Page]) = {
    val tinyListId = page match {
      case TinyListIdPage(uuid) => TinyListId(uuid)
    }

    App.component(
      AppProps(
        router = Some(router),
        tinyListId = Some(tinyListId)
      )
    )
  }

  def layout(c: RouterCtl[Page], r: Resolution[Page]) = r.render()
}
