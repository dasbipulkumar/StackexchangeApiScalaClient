package com.devstream.apiclients.stackoverflow.Apis

import com.devstream.apiclients.stackoverflow.parser.StackexchangeApiResponseParser
import com.devstream.apiclients.stackoverflow.request.{StackexchangeApiRequestHelper, StackexchangeApiRequestPackage, StackexchangeSite}
import com.devstream.apiclients.stackoverflow.request.StackexchangeSite.StackexchangeSite
import com.devstream.apiclients.stackoverflow.response.{BaseResponseWrapper, Item, QuestionTagsResponse, UserTimelineBase}

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

}
