/**
 * techwolf.cn All rights reserved.
 */
package com.omartech.laibicanRobot.model.message;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Comments for WeixinEventMessage.java
 * 
 * @author <a href="mailto:liujun@techwolf.cn">刘军</a>
 * @createTime 2014年8月14日 下午3:44:32
 */
public class WeixinEventMessage extends WeixinMessage {

    /**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(WeixinArticleMessage.class);

    protected String event;

    protected String eventKey;

    protected String ticket;

    /* (non-Javadoc)
     * @see cn.techwolf.kanzhun.weixin.mp.model.WeixinMessage#getType()
     */
    @Override
    public String getType() {
        return MESSAGE_TYPE_EVENT;
    }

    /* (non-Javadoc)
     * @see cn.techwolf.kanzhun.weixin.mp.model.WeixinMessage#getContent()
     */
    @Override
    public String getContent() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(XML_PROPERTIES_MSG_EVENT, event);
        if (StringUtils.isNotBlank(eventKey)) {
            jsonObject.put(XML_PROPERTIES_MSG_EVENT_KEY, eventKey);
        }
        if (StringUtils.isNotBlank(ticket)) {
            jsonObject.put(XML_PROPERTIES_MSG_TICKET, ticket);
        }

        return jsonObject.toString();
    }

    /* (non-Javadoc)
     * @see cn.techwolf.kanzhun.weixin.mp.model.WeixinMessage#setContent(java.lang.String)
     */
    @Override
    public void setContent(String content) {
        try {
            JSONObject jsonObject = JSONObject.fromObject(content);
            event = jsonObject.optString(XML_PROPERTIES_MSG_EVENT);
            eventKey = jsonObject.optString(XML_PROPERTIES_MSG_EVENT_KEY);
            ticket = jsonObject.optString(XML_PROPERTIES_MSG_TICKET);
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

        Element eventElement = root.addElement(XML_PROPERTIES_MSG_EVENT);
        eventElement.addCDATA(event);

        if (StringUtils.isNotBlank(eventKey)) {
            Element eventKeyElement = root.addElement(XML_PROPERTIES_MSG_EVENT_KEY);
            eventKeyElement.addCDATA(eventKey);
        }

        if (StringUtils.isNotBlank(ticket)) {
            Element ticketElement = root.addElement(XML_PROPERTIES_MSG_TICKET);
            ticketElement.addCDATA(ticket);
        }

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
        return eventKey;
    }

    /**
     * @return the event
     */
    public String getEvent() {
        return event;
    }

    /**
     * @param event the event to set
     */
    public void setEvent(String event) {
        this.event = event;
    }

    /**
     * @return the eventKey
     */
    public String getEventKey() {
        return eventKey;
    }

    /**
     * @param eventKey the eventKey to set
     */
    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    /**
     * @return the ticket
     */
    public String getTicket() {
        return ticket;
    }

    /**
     * @param ticket the ticket to set
     */
    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

}
