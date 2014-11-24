/**
 * techwolf.cn All rights reserved.
 */
package com.omartech.laibicanRobot.model.message;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.LoggerFactory;

/**
 * Comments for WeixinLocationEventMessage.java
 * 
 * @author <a href="mailto:liujun@techwolf.cn">刘军</a>
 * @createTime 2014年8月14日 下午4:32:41
 */
public class WeixinLocationEventMessage extends WeixinMessage {

    /**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(WeixinArticleMessage.class);

    private String event;

    private float latitude;

    private float longitude;

    private float precision;

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
        jsonObject.put(XML_PROPERTIES_MSG_LATITUDE, latitude);
        jsonObject.put(XML_PROPERTIES_MSG_LONGITUDE, longitude);
        jsonObject.put(XML_PROPERTIES_MSG_PRECISION, precision);

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
            latitude = (float) jsonObject.optDouble(XML_PROPERTIES_MSG_LATITUDE);
            longitude = (float) jsonObject.optDouble(XML_PROPERTIES_MSG_LONGITUDE);
            precision = (float) jsonObject.optDouble(XML_PROPERTIES_MSG_PRECISION);
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

        root.addElement(XML_PROPERTIES_MSG_LATITUDE).addText(String.valueOf(latitude));
        root.addElement(XML_PROPERTIES_MSG_LONGITUDE).addText(String.valueOf(longitude));
        root.addElement(XML_PROPERTIES_MSG_PRECISION).addText(String.valueOf(precision));

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
     * @return the latitude
     */
    public float getLatitude() {
        return latitude;
    }

    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    /**
     * @return the longitude
     */
    public float getLongitude() {
        return longitude;
    }

    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    /**
     * @return the precision
     */
    public float getPrecision() {
        return precision;
    }

    /**
     * @param precision the precision to set
     */
    public void setPrecision(float precision) {
        this.precision = precision;
    }

}
