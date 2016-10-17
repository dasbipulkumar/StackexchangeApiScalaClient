package com.devstream.apiclients.stackexchange.response

import org.json4s.DefaultFormats
import org.json4s.JsonAST.{JArray, JValue}

import scala.util.Try

/**
  * Created by bipulk on 10/17/16.
  */
class AnswerResponse(questionIdParam: String, bodyParam: String) extends Item {

  val questionId = questionIdParam
  val body = bodyParam


  def this() {
    this("", "")
  }

  override def parse(itemJValue: JValue): Option[Item] = {
    implicit val formats = DefaultFormats

    Try {

      AnswerResponse(itemJValue)

    }

  }.toOption


  override def toString = s"AnswerResponse(questionId=$questionId, body=$body)"
}

object AnswerResponse {
  def apply(itemJValue: JValue): AnswerResponse = {
    implicit val formats = DefaultFormats
    val questionId = (itemJValue \ "answer_id").extract[String]
    val body = (itemJValue \ "body").extract[String]

    new AnswerResponse(questionId, body)
  }
}