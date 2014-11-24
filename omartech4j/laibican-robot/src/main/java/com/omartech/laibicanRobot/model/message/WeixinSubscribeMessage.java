package com.omartech.laibicanRobot.model.message;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by guo on 14-8-27.
 */
public class WeixinSubscribeMessage extends WeixinMessage {

    /**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(WeixinArticleMessage.class);

    private String event;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    @Override
    public String getType() {
        return MESSAGE_TYPE_EVENT;
    }

    @Override
    public String getContent() {
        JSONObject jsonObject = new JSONObject();
        if (StringUtils.isNotBlank(event)) {
            jsonObject.put(XML_PROPERTIES_MSG_EVENT, event);
        }
        return jsonObject.toString();
    }

    @Override
    public void setContent(String content) {
        try {
            JSONObject jsonObject = JSONObject.fromObject(content);
            event = jsonObject.optString(XML_PROPERTIES_MSG_EVENT);
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    @Override
    public String toXml() {
        return null;
    }

    @Override
    public String getRecognizeType() {
        return getType();
    }

    @Override
    public String getRecognizeContent() {
        return event;
    }
}
