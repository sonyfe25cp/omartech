package com.omartech.laibicanRobot.model.reply;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateFormatUtils;

import java.util.Date;

/**
 * Created by guo on 14-8-23.
 */
public abstract class ReplyMessage {

    public static final String XML_PROPERTIES_MSG_TYPE = "MsgType";

    public static final String XML_PROPERTIES_MSG_FROM = "FromUserName";

    public static final String XML_PROPERTIES_MSG_TO = "ToUserName";

    public static final String XML_PROPERTIES_MSG_CREATE_TIME = "CreateTime";

    public static final String XML_PROPERTIES_MSG_CONTENT = "Content";

    public static final String XML_PROPERTIES_MSG_ARTICLE_COUNT = "ArticleCount";

    public static final String XML_PROPERTIES_MSG_ARTICLES = "Articles";

    public static final String XML_PROPERTIES_MSG_TITLE = "Title";

    public static final String XML_PROPERTIES_MSG_DESCRIPTION = "Description";

    public static final String XML_PROPERTIES_MSG_PIC_URL = "PicUrl";

    public static final String XML_PROPERTIES_MSG_ITEM = "item";

    public static final String XML_PROPERTIES_MSG_MEDIA_ID = "MediaId";

    public static final String XML_PROPERTIES_MSG_THUMB_MEDIA_ID = "ThumbMediaId";

    public static final String XML_PROPERTIES_MSG_URL = "Url";

    public static final String XML_PROPERTIES_MSG_IMAGE = "Image";

    public static final String XML_PROPERTIES_MSG_VOICE = "Voice";

    public static final String XML_PROPERTIES_MSG_VIDEO = "Video";

    public static final String XML_PROPERTIES_MSG_MUSIC = "Music";

    public static final String XML_PROPERTIES_MSG_MUSIC_URL = "MusicUrl";

    public static final String XML_PROPERTIES_MSG_HQMUSIC_URL = "HQMusicUrl";

    public static final String MESSAGE_TYPE_TEXT = "text";

    public static final String MESSAGE_TYPE_IMAGE = "image";

    public static final String MESSAGE_TYPE_VOICE = "voice";

    public static final String MESSAGE_TYPE_VIDEO = "video";

    public static final String MESSAGE_TYPE_MUSIC = "music";

    public static final String MESSAGE_TYPE_LOCATION = "location";

    public static final String MESSAGE_TYPE_LINK = "link";

    public static final String MESSAGE_TYPE_EVENT = "event";

    public static final String MESSAGE_TYPE_NEWS = "news";

    public static final String MESSAGE_EVENT_SUBSCRIBE = "subscribe";

    public static final String MESSAGE_EVENT_UNSUBSCRIBE = "unsubscribe";

    public static final String MESSAGE_EVENT_CLICK = "click";

    public static final String MESSAGE_EVENT_VIEW = "view";

    protected int id;

    // value fields

    protected long messageId;

    protected Date addTime;

    protected String fromName;

    protected String toName;

    public abstract String getType();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public abstract String getContent();

    public abstract void setContent(String content);

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public abstract String toXML();

    public int getDate8() {
        return NumberUtils.toInt(DateFormatUtils.format(addTime, "yyyyMMdd"));
    }

    public int getDate10() {
        return NumberUtils.toInt(DateFormatUtils.format(addTime, "yyyyMMddHH"));
    }

    @Override
    public String toString() {
        return "ReplyMessage{" +
                "id=" + id +
                ", messageId=" + messageId +
                ", addTime=" + addTime +
                ", fromName='" + fromName + '\'' +
                ", toName='" + toName + '\'' +
                '}';
    }
}
