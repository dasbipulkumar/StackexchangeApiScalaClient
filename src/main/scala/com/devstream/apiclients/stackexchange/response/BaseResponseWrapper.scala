package com.devstream.apiclients.stackexchange.response

import com.devstream.apiclients.stackexchange.parser.StackexchangeApiType._
import org.json4s.JsonAST.JValue

import scala.util.Try

/**
  * Created by bipulk on 9/25/16.
  */
class BaseResponseWrapper[T <: Item](backoffParam: Int, has_moreParam: Boolean, pageParam: Int,
                                     page_sizeParam: Int, quota_maxParam: Int, quota_remainingParam: Int,
                                     totalParam: Long, apiTypeParam: StackexchangeApiType, itemsParams: List[T]) {

  val backoff = backoffParam
  val hasMore = has_moreParam
  val page = pageParam
  val pageSize = page_sizeParam
  val quotaMax = quota_maxParam
  val quotaRemaining = quota_remainingParam
  val total = totalParam
  val apiType = apiTypeParam
  val items = itemsParams


  override def toString = s"BaseResponseWrapper(backoff=$backoff, hasMore=$hasMore, page=$page, pageSize=$pageSize, quotaMax=$quotaMax, quotaRemaining=$quotaRemaining, total=$total, apiType=$apiType, items=$items)"
}

class Item {

  def parse(itemJValue: JValue): Option[Item] = Try(new Item).toOption

}