package com.devstream.apiclients.stackexchange.request

import com.devstream.apiclients.stackexchange.request.StackexchangeSite.StackexchangeSite


/**
  * Created by bipulk on 9/23/16.
  */
class StackexchangeApiRequest(apiUrlParam: String, apiVersionParam: String, apiFunctionParam: String,
                              pageNoParam: Int, pageSizeParam: Int, applicationKeyParam: String,
                              accessTokenParam: String, filterParam: String, siteParam: String,
                              otherParamsParam: Map[String, String]) extends Serializable {

  val apiUrl = apiUrlParam
  val apiVersion = apiVersionParam
  val apiFunction = apiFunctionParam
  val pageNo = pageNoParam
  val pageSize = pageSizeParam
  val applicationKey = applicationKeyParam
  val accessToken = accessTokenParam
  val filter = filterParam
  val site = siteParam
  val otherParams = otherParamsParam

  def buildQueryString: String ={

    val queryBuilder = new StringBuilder
    queryBuilder ++= s"$apiUrl/$apiVersion/$apiFunction?"
    queryBuilder ++= s"key=$applicationKey&access_token=$accessToken&site=$site"
    queryBuilder ++= s"&page=$pageNo&pagesize=$pageSize&filter=$filter"

    otherParams.foreach(x=>queryBuilder ++= s"&${x._1}=${x._2}")

    queryBuilder.toString
  }


  override def toString = s"StackexchangeApiRequest($buildQueryString)"
}

object StackexchangeApiRequest {
  def apply(apiUrlParam: String, apiVersionParam: String, apiFunctionParam: String,
            pageNoParam: Int, pageSizeParam: Int, applicationKeyParam: String,
            accessTokenParam: String, filterParam: String, siteParam: StackexchangeSite,
            otherParamsParam: Map[String, String]): StackexchangeApiRequest = {
    new StackexchangeApiRequest(apiUrlParam, apiVersionParam, apiFunctionParam, pageNoParam,
      pageSizeParam, applicationKeyParam, accessTokenParam, filterParam, siteParam.toString, otherParamsParam)
  }
}

object StackexchangeSite extends Enumeration {
  type StackexchangeSite = Value
  val stackoverflow = Value
}
