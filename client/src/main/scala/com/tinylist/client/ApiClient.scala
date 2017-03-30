package com.tinylist.client

import upickle.default._

import org.scalajs.dom.ext.Ajax

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import upickle.default.{Reader, Writer, write => uwrite, read => uread}

object ApiClient extends autowire.Client[String, Reader, Writer] {
  override def doCall(req: Request): Future[String] = {
    Ajax.post(
      url = "/api/" + req.path.mkString("/"),
      data = write(req.args)
    ).map(_.responseText)
  }

  def write[Result: Writer](r: Result) = uwrite(r)
  def read[Result: Reader](p: String) = uread[Result](p)
}