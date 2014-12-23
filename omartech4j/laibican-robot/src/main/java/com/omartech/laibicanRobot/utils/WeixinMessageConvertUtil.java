/**
 * techwolf.cn All rights reserved.
 */
package com.omartech.laibicanRobot.utils;

import java.util.Date;

import com.omartech.laibicanRobot.model.message.*;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import org.slf4j.LoggerFactory;

/**
 * Comments for WeixinMessageConvertUtil.java
 *
 * @author <a href="mailto:liujun@techwolf.cn">刘军</a>
 * @createTime 2014年8月14日 下午4:49:38
 */
public class WeixinMessageConvertUtil {

    /**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(WeixinMessageConvertUtil.class);

    public static WeixinMessage convert2WeixinMessage(String xml) {
        logger.info("xml in : {}", xml);
        Document doc = null;
        try {
            doc = DocumentHelper.parseText(xml); // 将字符串转为XML
            Element root = doc.getRootElement();
            String type = root.elementTextTrim(WeixinMessage.XML_PROPERTIES_MSG_TYPE);
            WeixinMessage weixinMessage = null;
            if (StringUtils.equals(type, WeixinMessage.MESSAGE_TYPE_TEXT)) {
                weixinMessage = convert2WeixinTextMessage(root);
            } else if (StringUtils.equals(type, WeixinMessage.MESSAGE_TYPE_IMAGE)) {
                weixinMessage = convert2WeixinImageMessage(root);
            } else if (StringUtils.equals(type, WeixinMessage.MESSAGE_TYPE_VOICE)) {
                weixinMessage = convert2WeixinVoiceMessage(root);
            } else if (StringUtils.equals(type, WeixinMessage.MESSAGE_TYPE_VIDEO)) {
                weixinMessage = convert2WeixinVideoMessage(root);
            } else if (StringUtils.equals(type, WeixinMessage.MESSAGE_TYPE_LOCATION)) {
                weixinMessage = convert2WeixinLocationMessage(root);
            } else if (StringUtils.equals(type, WeixinMessage.MESSAGE_TYPE_LINK)) {
                weixinMessage = convert2WeixinLinkMessage(root);
            } else if (StringUtils.equals(type, WeixinMessage.MESSAGE_TYPE_EVENT)) {
                if (StringUtils.isNotBlank(root.elementTextTrim(WeixinMessage.XML_PROPERTIES_MSG_EVENT_KEY))) {
                    weixinMessage = convert2WeixinEventMessage(root);
                } else {
                    weixinMessage = convert2WeixinSubscribeMessage(root);
                }
            }
            if (weixinMessage != null) {
                weixinMessage.setFromName(root.elementTextTrim(WeixinMessage.XML_PROPERTIES_MSG_FROM));
                weixinMessage.setToName(root.elementTextTrim(WeixinMessage.XML_PROPERTIES_MSG_TO));
                weixinMessage.setMessageId(NumberUtils.toLong(root
                        .elementTextTrim(WeixinMessage.XML_PROPERTIES_MSG_ID)));
                weixinMessage.setAddTime(new Date(NumberUtils.toLong(root
                        .elementTextTrim(WeixinMessage.XML_PROPERTIES_MSG_CREATE_TIME)) * 1000));
                return weixinMessage;
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        return null;
    }

    private static WeixinTextMessage convert2WeixinTextMessage(Element element) {
        WeixinTextMessage weixinTextMessage = new WeixinTextMessage();
        weixinTextMessage
                .setText(element.elementTextTrim(WeixinMessage.XML_PROPERTIES_MSG_CONTENT));
        return weixinTextMessage;
    }

    private static WeixinImageMessage convert2WeixinImageMessage(Element element) {
        WeixinImageMessage weixinImageMessage = new WeixinImageMessage();
        weixinImageMessage.setPicUrl(element
                .elementTextTrim(WeixinMessage.XML_PROPERTIES_MSG_PIC_URL));
        weixinImageMessage.setMediaId(element
                .elementTextTrim(WeixinMessage.XML_PROPERTIES_MSG_MEDIA_ID));
        return weixinImageMessage;
    }

    private static WeixinVoiceMessage convert2WeixinVoiceMessage(Element element) {
        WeixinVoiceMessage weixinVoiceMessage = new WeixinVoiceMessage();
        weixinVoiceMessage.setFormat(element
                .elementTextTrim(WeixinMessage.XML_PROPERTIES_MSG_FORMAT));
        weixinVoiceMessage.setMediaId(element
                .elementTextTrim(WeixinMessage.XML_PROPERTIES_MSG_MEDIA_ID));
        return weixinVoiceMessage;
    }

    private static WeixinVideoMessage convert2WeixinVideoMessage(Element element) {
        WeixinVideoMessage weixinVideoMessage = new WeixinVideoMessage();
        weixinVideoMessage.setThumbMediaId(element
                .elementTextTrim(WeixinMessage.XML_PROPERTIES_MSG_THUMB_MEDIA_ID));
        weixinVideoMessage.setMediaId(element
                .elementTextTrim(WeixinMessage.XML_PROPERTIES_MSG_MEDIA_ID));
        return weixinVideoMessage;
    }

    private static WeixinLocationMessage convert2WeixinLocationMessage(Element element) {
        WeixinLocationMessage weixinLocationMessage = new WeixinLocationMessage();
        weixinLocationMessage.setLocationX(NumberUtils.toFloat(element
                .elementTextTrim(WeixinMessage.XML_PROPERTIES_MSG_LOCATION_X)));
        weixinLocationMessage.setLocationY(NumberUtils.toFloat(element
                .elementTextTrim(WeixinMessage.XML_PROPERTIES_MSG_LOCATION_Y)));
        weixinLocationMessage.setScale(NumberUtils.toInt(element
                .elementTextTrim(WeixinMessage.XML_PROPERTIES_MSG_SCALE)));
        weixinLocationMessage.setLabel(element
                .elementTextTrim(WeixinMessage.XML_PROPERTIES_MSG_LABEL));
        return weixinLocationMessage;
    }

    private static WeixinLinkMessage convert2WeixinLinkMessage(Element element) {
        WeixinLinkMessage weixinLinkMessage = new WeixinLinkMessage();
        weixinLinkMessage.setTitle(element.elementTextTrim(WeixinMessage.XML_PROPERTIES_MSG_TITLE));
        weixinLinkMessage.setDescription(element
                .elementTextTrim(WeixinMessage.XML_PROPERTIES_MSG_DESCRIPTION));
        weixinLinkMessage.setUrl(element.elementTextTrim(WeixinMessage.XML_PROPERTIES_MSG_URL));
        return weixinLinkMessage;
    }


    private static WeixinSubscribeMessage convert2WeixinSubscribeMessage(Element element) {
        WeixinSubscribeMessage message = new WeixinSubscribeMessage();
        message.setEvent(element.elementTextTrim(WeixinMessage.XML_PROPERTIES_MSG_EVENT));
        return message;
    }


    private static WeixinMessage convert2WeixinEventMessage(Element element) {
        String event = element.elementTextTrim(WeixinMessage.XML_PROPERTIES_MSG_EVENT);
        if (StringUtils.equals(event, "LOCATION")) {
            WeixinLocationEventMessage weixinLocationEventMessage = new WeixinLocationEventMessage();
            weixinLocationEventMessage.setEvent(event);
            weixinLocationEventMessage.setLatitude(NumberUtils.toFloat(element
                    .elementTextTrim(WeixinMessage.XML_PROPERTIES_MSG_LATITUDE)));
            weixinLocationEventMessage.setLongitude(NumberUtils.toFloat(element
                    .elementTextTrim(WeixinMessage.XML_PROPERTIES_MSG_LONGITUDE)));
            weixinLocationEventMessage.setPrecision(NumberUtils.toFloat(element
                    .elementTextTrim(WeixinMessage.XML_PROPERTIES_MSG_PRECISION)));
            return weixinLocationEventMessage;
        } else {
            WeixinEventMessage weixinEventMessage = new WeixinEventMessage();
            weixinEventMessage.setEvent(event);
            weixinEventMessage.setEventKey(element
                    .elementTextTrim(WeixinMessage.XML_PROPERTIES_MSG_EVENT_KEY));
            weixinEventMessage.setTicket(element
                    .elementTextTrim(WeixinMessage.XML_PROPERTIES_MSG_TICKET));
            return weixinEventMessage;
        }
    }
}
