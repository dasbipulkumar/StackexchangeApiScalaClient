package com.devstream.apiclients.stackoverflow.request


import com.devstream.apiclients.stackoverflow.gzip.ResponseDecompressor
import org.apache.commons.httpclient.methods.GetMethod
import org.apache.commons.httpclient.params.HttpConnectionManagerParams
import org.apache.commons.httpclient.{HttpClient, SimpleHttpConnectionManager}
import org.json4s._
import org.json4s.jackson.JsonMethods._

import scala.util.Try

/**
  * Created by bipulk on 9/23/16.
  */

object StackexchangeApiRequestHelper {

  val maxRetries = 3

  def getStackexchangeRestResponse(request: StackexchangeApiRequest): Option[JValue] = {

    httpGetJson(executeRequest(request.buildQueryString).get)

  }

  private def httpGetJson(method: GetMethod): Option[JValue] = {
    val status = method.getStatusCode
    if (status == 200) {
      // ignored parsing errors if any, because we can not do anything about them anyway.
      Try (parse(ResponseDecompressor.decompressGZIPContent(method.getResponseBody))).toOption
    } else {
      println("Request failed with status:" + status + "Response:"
        + method.getResponseHeaders.mkString("\n") +
        "\nResponseBody " + method.getResponseBodyAsString)
      None
    }
  }

  /**
    * Helper for accessing Java - Apache Http client.
    * (It it important to stick with the current version and all.)
    */

  private def executeRequest(url: String): Option[GetMethod] = {

    def retryRequest(url: String, tries: Int): Option[GetMethod] = {
      val start = System.currentTimeMillis()
      val client = new HttpClient(getConnectionManager)
      val method = createGetMethod(url)
      val mayBeMethod = Try(client.executeMethod(method))
      if (mayBeMethod.isSuccess) {
        println(s"Request to $url took ${System.currentTimeMillis() - start}")
        Some(method)
      } else {
        if (tries >= 0) {
          println(s"Retrying request to $url")
          retryRequest(url, tries - 1)
        } else {
          println(s"Skipping request to $url after $maxRetries")
          None
        }
      }

    }
    retryRequest(url, maxRetries)
  }

  private def createGetMethod(url: String): GetMethod = {
    val method = new GetMethod(url)
    method.setRequestHeader("Content-Type", "application/json; charset=utf-8")
    method
  }

  private def getConnectionManager = {
    val httpConnectionManager = new SimpleHttpConnectionManager()

    val managerParams = new HttpConnectionManagerParams
    managerParams.setConnectionTimeout(10 * 1000)

    httpConnectionManager.setParams(managerParams)
    httpConnectionManager
  }

}
