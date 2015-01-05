/**
 * techwolf.cn All rights reserved.
 */
package com.omartech.laibicanRobot.model.message;

import com.omartech.laibicanRobot.model.AppEnum;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Comments for WeixinMessage.java
 *
 * @author <a href="mailto:liujun@techwolf.cn">刘军</a>
 * @createTime 2014年8月14日 上午11:14:55
 */
public abstract class WeixinMessage {

    private AppEnum appEnum;

    /**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(WeixinArticleMessage.class);

    public static final String XML_PROPERTIES_MSG_ID = "MsgId";

    public static final String XML_PROPERTIES_MSG_TYPE = "MsgType";

    public static final String XML_PROPERTIES_MSG_FROM = "FromUserName";

    public static final String XML_PROPERTIES_MSG_TO = "ToUserName";

    public static final String XML_PROPERTIES_MSG_CREATE_TIME = "CreateTime";

    public static final String XML_PROPERTIES_MSG_EVENT = "Event";

    public static final String XML_PROPERTIES_MSG_EVENT_KEY = "EventKey";

    public static final String XML_PROPERTIES_MSG_TICKET = "Ticket";

    public static final String XML_PROPERTIES_MSG_CONTENT = "Content";

    public static final String XML_PROPERTIES_MSG_ARTICLE_COUNT = "ArticleCount";

    public static final String XML_PROPERTIES_MSG_ARTICLES = "Articles";

    public static final String XML_PROPERTIES_MSG_TITLE = "Title";

    public static final String XML_PROPERTIES_MSG_DESCRIPTION = "Description";

    public static final String XML_PROPERTIES_MSG_PIC_URL = "PicUrl";

    public static final String XML_PROPERTIES_MSG_ITEM = "item";

    public static final String XML_PROPERTIES_MSG_MEDIA_ID = "MediaId";

    public static final String XML_PROPERTIES_MSG_FORMAT = "Format";

    public static final String XML_PROPERTIES_MSG_THUMB_MEDIA_ID = "ThumbMediaId";

    public static final String XML_PROPERTIES_MSG_LOCATION_X = "Location_X";

    public static final String XML_PROPERTIES_MSG_LOCATION_Y = "Location_Y";

    public static final String XML_PROPERTIES_MSG_SCALE = "Scale";

    public static final String XML_PROPERTIES_MSG_LABEL = "Label";

    public static final String XML_PROPERTIES_MSG_URL = "Url";

    public static final String XML_PROPERTIES_MSG_LATITUDE = "Latitude";

    public static final String XML_PROPERTIES_MSG_LONGITUDE = "Longitude";

    public static final String XML_PROPERTIES_MSG_PRECISION = "Precision";

    public static final String MESSAGE_TYPE_TEXT = "text";

    public static final String MESSAGE_TYPE_IMAGE = "image";

    public static final String MESSAGE_TYPE_VOICE = "voice";

    public static final String MESSAGE_TYPE_VIDEO = "video";

    public static final String MESSAGE_TYPE_LOCATION = "location";

    public static final String MESSAGE_TYPE_LINK = "link";

    public static final String MESSAGE_TYPE_EVENT = "event";

    public static final String MESSAGE_TYPE_NEWS = "news";

    public static final String MESSAGE_EVENT_SUBSCRIBE = "subscribe";

    public static final String MESSAGE_EVENT_UNSUBSCRIBE = "unsubscribe";

    public static final String MESSAGE_EVENT_CLICK = "click";

    public static final String MESSAGE_EVENT_VIEW = "view";

    protected long id;

    protected String fromName;

    protected String toName;

    protected long messageId;

    protected Date addTime;

    /**
     * @return the type
     */
    public abstract String getType();

    /**
     * @return the content
     */
    public abstract String getContent();

    /**
     * @return the content
     */
    public abstract void setContent(String content);

    /**
     * @return the content
     */
    public abstract String toXml();

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the fromName
     */
    public String getFromName() {
        return fromName;
    }

    /**
     * @param fromName the fromName to set
     */
    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    /**
     * @return the toName
     */
    public String getToName() {
        return toName;
    }

    /**
     * @param toName the toName to set
     */
    public void setToName(String toName) {
        this.toName = toName;
    }

    /**
     * @return the messageId
     */
    public long getMessageId() {
        return messageId;
    }

    /**
     * @param messageId the messageId to set
     */
    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    /**
     * @return the addTime
     */
    public Date getAddTime() {
        return addTime;
    }

    /**
     * @param addTime the addTime to set
     */
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public abstract String getRecognizeType();

    public abstract String getRecognizeContent();

    public int getDate8() {
        return NumberUtils.toInt(DateFormatUtils.format(addTime, "yyyyMMdd"));
    }

    public int getDate10() {
        return NumberUtils.toInt(DateFormatUtils.format(addTime, "yyyyMMddHH"));
    }

    @Override
    public String toString() {
        return "WeixinMessage{" +
                "id=" + id +
                ", fromName='" + fromName + '\'' +
                ", toName='" + toName + '\'' +
                ", messageId=" + messageId +
                ", addTime=" + addTime +
                '}';
    }

    public AppEnum getAppEnum() {
        return appEnum;
    }

    public void setAppEnum(AppEnum appEnum) {
        this.appEnum = appEnum;
    }
}
