package com.devstream.apiclients.stackexchange.api

import com.devstream.apiclients.stackexchange.request.StackexchangeSite

/**
  * Created by bipulk on 9/26/16.
  */
object StackexchangeApiIntegrationTestSuite {

  def main(args: Array[String]) {

    //println(StackexchangeApiPackage.getMyTimeline("U4DMV*8nvpm3EOpvf69Rxw((", "ke1nd1e1AIs4O3T3wbx3DQ))", StackexchangeSite.stackoverflow, 1 ,100))

    val questionIds = List.fill(3)("29608319")
    //println(buildQuestionTagsRequest("key","token",StackexchangeSite.stackoverflow,questionIds).buildQueryString)

    println(StackexchangeApiPackage.getQuestionTags("U4DMV*8nvpm3EOpvf69Rxw((", "ke1nd1e1AIs4O3T3wbx3DQ))", StackexchangeSite.stackoverflow, questionIds))


  }

}
