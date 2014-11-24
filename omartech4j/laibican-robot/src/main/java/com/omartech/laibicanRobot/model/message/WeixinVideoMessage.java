/**
 * techwolf.cn All rights reserved.
 */
package com.omartech.laibicanRobot.model.message;

import org.slf4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import net.sf.json.JSONObject;
import org.slf4j.LoggerFactory;

/**
 * Comments for WeixinVideoMessage.java
 * 
 * @author <a href="mailto:liujun@techwolf.cn">刘军</a>
 * @createTime 2014年8月14日 下午3:14:12
 */
public class WeixinVideoMessage extends WeixinMessage {

    /**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(WeixinArticleMessage.class);

    private String thumbMediaId;

    private String mediaId;

    /* (non-Javadoc)
     * @see cn.techwolf.kanzhun.weixin.mp.model.WeixinMessage#getType()
     */
    @Override
    public String getType() {
        return MESSAGE_TYPE_VIDEO;
    }

    /* (non-Javadoc)
     * @see cn.techwolf.kanzhun.weixin.mp.model.WeixinMessage#getContent()
     */
    @Override
    public String getContent() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(XML_PROPERTIES_MSG_THUMB_MEDIA_ID, thumbMediaId);
        jsonObject.put(XML_PROPERTIES_MSG_MEDIA_ID, mediaId);
        return jsonObject.toString();
    }

    /* (non-Javadoc)
     * @see cn.techwolf.kanzhun.weixin.mp.model.WeixinMessage#setContent(java.lang.String)
     */
    @Override
    public void setContent(String content) {
        try {
            JSONObject jsonObject = JSONObject.fromObject(content);
            thumbMediaId = jsonObject.optString(XML_PROPERTIES_MSG_THUMB_MEDIA_ID);
            mediaId = jsonObject.optString(XML_PROPERTIES_MSG_MEDIA_ID);
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    /* (non-Javadoc)
     * @see cn.techwolf.kanzhun.weixin.mp.model.WeixinMessage#toXml()
     */
    @Override
    public String toXml() {
        Document doc = DocumentHelper.createDocument();
        Element root = doc.addElement("xml");
        Element fromElement = root.addElement(XML_PROPERTIES_MSG_FROM);
        fromElement.addCDATA(fromName);

        Element toElement = root.addElement(XML_PROPERTIES_MSG_TO);
        toElement.addCDATA(toName);

        Element typeElement = root.addElement(XML_PROPERTIES_MSG_TYPE);
        typeElement.addCDATA(getType());

        Element mediaIdElement = root.addElement(XML_PROPERTIES_MSG_MEDIA_ID);
        mediaIdElement.addCDATA(mediaId);

        Element thumbMediaIdElement = root.addElement(XML_PROPERTIES_MSG_THUMB_MEDIA_ID);
        thumbMediaIdElement.addCDATA(thumbMediaId);

        Element timeElement = root.addElement(XML_PROPERTIES_MSG_CREATE_TIME);
        timeElement.addText(String.valueOf(System.currentTimeMillis() / 1000));

        return doc.asXML();
    }

    @Override
    public String getRecognizeType() {
        return getType();
    }

    @Override
    public String getRecognizeContent() {
        return "";
    }

    /**
     * @return the thumbMediaId
     */
    public String getThumbMediaId() {
        return thumbMediaId;
    }

    /**
     * @param thumbMediaId the thumbMediaId to set
     */
    public void setThumbMediaId(String thumbMediaId) {
        this.thumbMediaId = thumbMediaId;
    }

    /**
     * @return the mediaId
     */
    public String getMediaId() {
        return mediaId;
    }

    /**
     * @param mediaId the mediaId to set
     */
    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

}
