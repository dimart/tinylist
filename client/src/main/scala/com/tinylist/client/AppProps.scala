package com.tinylist
package client

import com.tinylist.api._
import japgolly.scalajs.react.extra.router.RouterCtl

case class AppProps(router: Option[RouterCtl[Page]], tinyListId: Option[TinyListId])
