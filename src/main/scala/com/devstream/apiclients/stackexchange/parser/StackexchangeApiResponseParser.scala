package com.devstream.apiclients.stackexchange.parser

import com.devstream.apiclients.stackexchange.response.{BaseResponseWrapper, Item}
import org.json4s.DefaultFormats
import org.json4s.JsonAST.{JArray, JValue}


/**
  * Created by bipulk on 9/23/16.
  */

object StackexchangeApiResponseParser {

  def parseResponse[T<:Item](rawResponse: JValue)(implicit m: scala.reflect.Manifest[T]): BaseResponseWrapper[Item] = {

    implicit val formats = DefaultFormats
    val backoff = (rawResponse \ "backoff").extractOrElse[Int](0)
    val has_more = (rawResponse \ "has_more").extractOrElse[Boolean](false)
    val page = (rawResponse \ "page").extractOrElse[Int](0)
    val page_size = (rawResponse \ "page_size").extractOrElse[Int](0)
    val quota_max = (rawResponse \ "quota_max").extractOrElse[Int](0)
    val quota_remaining = (rawResponse \ "quota_remaining").extractOrElse[Int](0)
    val apiType = StackexchangeApiType.withName((rawResponse \ "type").extractOrElse[String]("none"))

    val itemType = m.runtimeClass.newInstance.asInstanceOf[T]
    val items = (rawResponse \ "items").extract[JArray].arr.map(x => itemType.parse(x)).
                filter(_.isDefined).filter(_.get.getClass.getName != "com.devstream.apiclients.stackoverflow.response.ItemType")map(_.get)

    val total = items.size

    new BaseResponseWrapper(backoff, has_more, page, page_size, quota_max, quota_remaining, total, apiType, items)
  }

}
