package com.tinylist.api

trait AutowireApi {
  def save(tinyList: TinyList): TinyListId
  def fetch(tinyListId: TinyListId): TinyList
}
