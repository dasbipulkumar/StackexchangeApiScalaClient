package com.devstream.apiclients.stackoverflow.response

import com.devstream.apiclients.stackoverflow.response.UserTimelinePostType.UserTimelinePostType
import com.devstream.apiclients.stackoverflow.response.UserTimelineType.UserTimelineType
import org.json4s.DefaultFormats
import org.json4s.JsonAST.JValue

import scala.util.Try

/**
  * Created by bipulk on 9/24/16.
  */


class UserTimelineBase(eventIdParam: String, userIdParam: String, timeStampParam: Long, timelineTypeParam: UserTimelineType) extends Item {

  val eventId = eventIdParam
  val userId = userIdParam
  val timelineType = timelineTypeParam
  val timeStamp = timeStampParam

  def this() {
    this("", "", -1, UserTimelineType.none)
  }

  override def parse(itemJValue: JValue): Option[Item] = {
    implicit val formats = DefaultFormats
    val timelineType = UserTimelineType.withName((itemJValue \ "timeline_type").extractOrElse[String]("none"))
    Try {
      timelineType match {
        case UserTimelineType.asked => UserTimeLineQuestionAsked(itemJValue)

        case UserTimelineType.answered => UserTimeLineQuestionAnswered(itemJValue)

        case UserTimelineType.badge => UserTimeLineBadge(itemJValue)

        case UserTimelineType.commented => UserTimeLineComment(itemJValue)

        case _ => new Item
      }

    }.toOption
  }




}

object UserTimelineType extends Enumeration {
  type UserTimelineType = Value
  val asked, badge, answered, commented, revision, reviewed, suggested, accepted, none = Value

}

object UserTimelinePostType extends Enumeration {
  type UserTimelinePostType = Value
  val question, answer, none = Value

}

class UserTimeLineQuestionAsked(eventIdParam: String, userIdParam: String, timeStampParam: Long, timelineTypeParam: UserTimelineType,
                                postTypeParam: UserTimelinePostType, linkParam: String, questionIdParam: String, titleParam: String) extends
  UserTimelineBase(eventIdParam, userIdParam, timeStampParam, timelineTypeParam) {
  val postType = postTypeParam
  val link = linkParam
  val questionId = questionIdParam
  val title = titleParam



}

object UserTimeLineQuestionAsked {
  def apply(itemJValue: JValue): UserTimeLineQuestionAsked = {
    implicit val formats = DefaultFormats
    val eventId = (itemJValue \ "post_id").extract[String]
    val timelineType = UserTimelineType.withName((itemJValue \ "timeline_type").extractOrElse[String]("none"))
    val userId = (itemJValue \ "user_id").extract[String]
    val timeStamp = (itemJValue \ "creation_date").extract[Long]
    val postType = UserTimelinePostType.withName((itemJValue \ "post_type").extractOrElse[String]("none"))
    val link = (itemJValue \ "link").extract[String]
    val title = (itemJValue \ "title").extract[String]
    val questionId = eventId
    new UserTimeLineQuestionAsked(eventId, userId, timeStamp, timelineType, postType, link, questionId, title)
  }
}


class UserTimeLineQuestionAnswered(eventIdParam: String, userIdParam: String, timeStampParam: Long, timelineTypeParam: UserTimelineType,
                                   postTypeParam: UserTimelinePostType, linkParam: String, questionIdParam: String, titleParam: String) extends
  UserTimelineBase(eventIdParam, userIdParam, timeStampParam, timelineTypeParam) {
  val postType = postTypeParam
  val link = linkParam
  val questionId = questionIdParam
  val title = titleParam

}

object UserTimeLineQuestionAnswered {
  def apply(itemJValue: JValue): UserTimeLineQuestionAnswered = {
    implicit val formats = DefaultFormats
    val eventId = (itemJValue \ "post_id").extract[String]
    val timelineType = UserTimelineType.withName((itemJValue \ "timeline_type").extractOrElse[String]("none"))
    val userId = (itemJValue \ "user_id").extract[String]
    val timeStamp = (itemJValue \ "creation_date").extract[Long]
    val postType = UserTimelinePostType.withName((itemJValue \ "post_type").extractOrElse[String]("none"))
    val link = (itemJValue \ "link").extract[String]
    val title = (itemJValue \ "title").extract[String]

    val regex = "questions\\/(.*?)\\/"
    var questionId = ""
    val pattern = java.util.regex.Pattern.compile(regex)
    val matcher = pattern.matcher(link)
    if (matcher.find()) {
      questionId = matcher.group(1)
    }


    new UserTimeLineQuestionAnswered(eventId, userId, timeStamp, timelineType, postType, link, questionId, title)
  }
}


class UserTimeLineBadge(eventIdParam: String, userIdParam: String, timeStampParam: Long, timelineTypeParam: UserTimelineType,
                        linkParam: String, detailParam: String) extends
  UserTimelineBase(eventIdParam, userIdParam, timeStampParam, timelineTypeParam) {

  val link = linkParam
  val detail = detailParam

}

object UserTimeLineBadge {
  def apply(itemJValue: JValue): UserTimeLineBadge = {
    implicit val formats = DefaultFormats
    val badgeId = (itemJValue \ "badge_id").extract[String]
    val timelineType = UserTimelineType.withName((itemJValue \ "timeline_type").extractOrElse[String]("none"))
    val userId = (itemJValue \ "user_id").extract[String]
    val timeStamp = (itemJValue \ "creation_date").extract[Long]
    val link = (itemJValue \ "link").extract[String]
    val detail = (itemJValue \ "detail").extract[String]

    val regex = "(.*?)\\/(.*?)\\/(.*?)\\/(.*?)\\/"
    var site = ""
    val pattern = java.util.regex.Pattern.compile(regex)
    val matcher = pattern.matcher(link)
    if (matcher.find()) {
      site = matcher.group(3)
    }

    val badgeLink = s"http://$site/badges/$badgeId?userId=$userId"
    new UserTimeLineBadge(userId + "_" + badgeId, userId, timeStamp, timelineType, badgeLink, detail)
  }
}

class UserTimeLineComment(eventIdParam: String, postIdParam:String, userIdParam: String, timeStampParam: Long, timelineTypeParam: UserTimelineType,
                                   postTypeParam: UserTimelinePostType, linkParam: String, questionIdParam: String, titleParam: String, detailParam: String) extends
  UserTimelineBase(eventIdParam, userIdParam, timeStampParam, timelineTypeParam) {
  val postId = postIdParam
  val postType = postTypeParam
  val link = linkParam
  val questionId = questionIdParam
  val title = titleParam
  val detail = detailParam

}

object UserTimeLineComment {
  def apply(itemJValue: JValue): UserTimeLineComment = {
    implicit val formats = DefaultFormats
    val commentId = (itemJValue \ "comment_id").extract[String]
    val postId = (itemJValue \ "post_id").extract[String]
    val timelineType = UserTimelineType.withName((itemJValue \ "timeline_type").extractOrElse[String]("none"))
    val userId = (itemJValue \ "user_id").extract[String]
    val timeStamp = (itemJValue \ "creation_date").extract[Long]
    val postType = UserTimelinePostType.withName((itemJValue \ "post_type").extractOrElse[String]("none"))
    val link = (itemJValue \ "link").extract[String]
    val title = (itemJValue \ "title").extract[String]
    val detail = (itemJValue \ "detail").extract[String]
    val regex = "questions\\/(.*?)\\/"
    var questionId = ""
    val pattern = java.util.regex.Pattern.compile(regex)
    val matcher = pattern.matcher(link)
    if (matcher.find()) {
      questionId = matcher.group(1)
    }


    new UserTimeLineComment(commentId, postId, userId, timeStamp, timelineType, postType, link, questionId, title, detail)
  }
}
