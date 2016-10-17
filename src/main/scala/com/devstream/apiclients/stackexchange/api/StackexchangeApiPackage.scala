package com.devstream.apiclients.stackexchange.api

import com.devstream.apiclients.stackexchange.parser.StackexchangeApiResponseParser
import com.devstream.apiclients.stackexchange.request.StackexchangeSite.StackexchangeSite
import com.devstream.apiclients.stackexchange.request.{StackexchangeApiRequestHelper, StackexchangeApiRequestPackage}
import com.devstream.apiclients.stackexchange.response._

/**
  * Created by bipulk on 9/23/16.
  */
object StackexchangeApiPackage {


  def getMyTimeline(key: String, accessToken: String, site: StackexchangeSite, pageNo: Int, pageSize: Int): BaseResponseWrapper[Item] = {

    val response = StackexchangeApiRequestHelper.getStackexchangeRestResponse(
      StackexchangeApiRequestPackage.buildMyTimeLineRequest(key, accessToken, site, pageNo, pageSize))
    StackexchangeApiResponseParser.parseResponse[UserTimelineBase](response.get)

  }

  def getQuestionTags(key: String, accessToken: String,site: StackexchangeSite, questionIds: List[String]): BaseResponseWrapper[Item] = {

    val response = StackexchangeApiRequestHelper.getStackexchangeRestResponse(
      StackexchangeApiRequestPackage.buildQuestionTagsRequest(key, accessToken, site, questionIds))
    StackexchangeApiResponseParser.parseResponse[QuestionTagsResponse](response.get)

  }

  def getAnswers(key: String, accessToken: String,site: StackexchangeSite, answerIds: List[String]): BaseResponseWrapper[Item] = {

    val response = StackexchangeApiRequestHelper.getStackexchangeRestResponse(
      StackexchangeApiRequestPackage.buildAnswersRequest(key, accessToken, site, answerIds))
    StackexchangeApiResponseParser.parseResponse[AnswerResponse](response.get)

  }

}
