package com.devstream.apiclients.stackexchange.response

import org.json4s.DefaultFormats
import org.json4s.JsonAST.{JArray, JValue}

import scala.util.Try

/**
  * Created by bipulk on 9/26/16.
  */

class QuestionTagsResponse(questionIdParam: String, tagsParam: List[String]) extends Item {

  val questionId = questionIdParam
  val tags = tagsParam


  def this() {
    this("", List.empty[String])
  }

  override def parse(itemJValue: JValue): Option[Item] = {
    implicit val formats = DefaultFormats

    Try {

      QuestionTagsResponse(itemJValue)

    }

  }.toOption


  override def toString = s"QuestionTagsResponse(questionId=$questionId, tags=$tags)"
}

object QuestionTagsResponse {
  def apply(itemJValue: JValue): QuestionTagsResponse = {
    implicit val formats = DefaultFormats
    val questionId = (itemJValue \ "question_id").extract[String]
    val tags = (itemJValue \ "tags").extract[JArray].arr.map(_.extract[String])

    new QuestionTagsResponse(questionId, tags)
  }
}