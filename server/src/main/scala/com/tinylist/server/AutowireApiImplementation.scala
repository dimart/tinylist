package com.tinylist.server

import java.nio.ByteBuffer
import java.util.{Base64, UUID}

import com.tinylist.api.{AutowireApi, TinyList, TinyListId}

class AutowireApiImplementation extends AutowireApi {
  private var savedLists = Seq[TinyList]()

  def save(tinyList: TinyList): TinyListId = {
    savedLists :+= tinyList
    val uuid = randomUrlFirendlyBase64UUID
    TinyListId(uuid)
  }

  /*
    Thanks to:
    https://github.com/scalacenter/scastie/blob/master/balancer/src/main/scala/com.olegych.scastie.balancer/SnippetsContainer.scala#L259
   */
  private def randomUrlFirendlyBase64UUID: String = {
    def toBase64(uuid: UUID): String = {
      val (high, low) =
        (uuid.getMostSignificantBits, uuid.getLeastSignificantBits)
      val buffer = ByteBuffer.allocate(java.lang.Long.BYTES * 2)
      buffer.putLong(high)
      buffer.putLong(low)
      val encoded = Base64.getMimeEncoder.encodeToString(buffer.array())
      encoded.take(encoded.length - 2)
    }

    var res: String = null
    val allowed = ('a' to 'z').toSet ++ ('A' to 'Z').toSet ++ ('0' to '9').toSet

    while (res == null || res.exists(c => !allowed.contains(c))) {
      val uuid = java.util.UUID.randomUUID()
      res = toBase64(uuid)
    }

    res
  }
}
