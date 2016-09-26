package com.devstream.apiclients.stackexchange.request


import com.devstream.apiclients.stackexchange.request.StackexchangeSite.StackexchangeSite

/**
  * Created by bipulk on 9/23/16.
  */

trait StackexchangeApi {

  val apiUrl = "https://api.stackexchange.com"
  val apiVersion = "2.2"

}

object StackexchangeApiRequestPackage extends StackexchangeApi {

  def buildMyTimeLineRequest(key: String, accessToken: String, site: StackexchangeSite, pageNo: Int, pageSize: Int): StackexchangeApiRequest = {

    val apiFunction = "me/timeline"
    val apiFilter = "8RBcNOXG"

    StackexchangeApiRequest(apiUrl, apiVersion, apiFunction, pageNo, pageSize, key, accessToken, apiFilter, site, Map.empty)

  }

  def buildQuestionTagsRequest(key: String, accessToken: String,site: StackexchangeSite, questionIds: List[String]): StackexchangeApiRequest = {

    val questionIdBuilder = new StringBuilder

    val questionIdsSet = questionIds.toSet
    questionIdsSet.foreach(x => questionIdBuilder.append(s"$x;"))


    val apiFunction = s"questions/${questionIdBuilder.toString().substring(0,questionIdBuilder.toString().size-1)}"
    val apiFilter = "!w-2.MGu7E2Cg((vSpA"
    StackexchangeApiRequest(apiUrl, apiVersion, apiFunction, 1, questionIdsSet.size, key, accessToken, apiFilter, site, Map.empty)
  }


}
