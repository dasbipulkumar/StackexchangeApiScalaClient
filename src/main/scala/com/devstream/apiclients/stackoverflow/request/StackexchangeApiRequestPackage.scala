package com.devstream.apiclients.stackoverflow.request


import com.devstream.apiclients.stackoverflow.Apis.StackexchangeApiPackage
import com.devstream.apiclients.stackoverflow.request.StackexchangeSite.StackexchangeSite

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

  def main(args: Array[String]) {

    //println(StackexchangeApiPackage.getMyTimeline("U4DMV*8nvpm3EOpvf69Rxw((", "ke1nd1e1AIs4O3T3wbx3DQ))", StackexchangeSite.stackoverflow, 1 ,100))

    val questionIds = List.fill(3)("29608319")
    //println(buildQuestionTagsRequest("key","token",StackexchangeSite.stackoverflow,questionIds).buildQueryString)

    println(StackexchangeApiPackage.getQuestionTags("U4DMV*8nvpm3EOpvf69Rxw((", "ke1nd1e1AIs4O3T3wbx3DQ))", StackexchangeSite.stackoverflow,questionIds))

  }

}
