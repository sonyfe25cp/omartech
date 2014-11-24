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
 * Comments for WeixinLocationMessage.java
 * 
 * @author <a href="mailto:liujun@techwolf.cn">刘军</a>
 * @createTime 2014年8月14日 下午3:17:53
 */
public class WeixinLocationMessage extends WeixinMessage {

    /**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(WeixinArticleMessage.class);

    private float locationX;

    private float locationY;

    private int scale;

    private String label;

    /* (non-Javadoc)
     * @see cn.techwolf.kanzhun.weixin.mp.model.WeixinMessage#getType()
     */
    @Override
    public String getType() {
        return MESSAGE_TYPE_LOCATION;
    }

    /* (non-Javadoc)
     * @see cn.techwolf.kanzhun.weixin.mp.model.WeixinMessage#getContent()
     */
    @Override
    public String getContent() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(XML_PROPERTIES_MSG_LOCATION_X, locationX);
        jsonObject.put(XML_PROPERTIES_MSG_LOCATION_Y, locationY);
        jsonObject.put(XML_PROPERTIES_MSG_SCALE, scale);
        jsonObject.put(XML_PROPERTIES_MSG_LABEL, label);
        return jsonObject.toString();
    }

    /* (non-Javadoc)
     * @see cn.techwolf.kanzhun.weixin.mp.model.WeixinMessage#setContent(java.lang.String)
     */
    @Override
    public void setContent(String content) {
        try {
            JSONObject jsonObject = JSONObject.fromObject(content);
            locationX = (float) jsonObject.optDouble(XML_PROPERTIES_MSG_LOCATION_X);
            locationY = (float) jsonObject.optDouble(XML_PROPERTIES_MSG_LOCATION_Y);
            scale = jsonObject.optInt(XML_PROPERTIES_MSG_SCALE);
            label = jsonObject.optString(XML_PROPERTIES_MSG_MEDIA_ID);
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

        Element locationXElement = root.addElement(XML_PROPERTIES_MSG_LOCATION_X);
        locationXElement.addText(String.valueOf(locationX));

        Element locationYElement = root.addElement(XML_PROPERTIES_MSG_LOCATION_Y);
        locationYElement.addText(String.valueOf(locationY));

        Element scaleElement = root.addElement(XML_PROPERTIES_MSG_SCALE);
        scaleElement.addText(String.valueOf(scale));

        Element labelElement = root.addElement(XML_PROPERTIES_MSG_LABEL);
        labelElement.addCDATA(label);

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
     * @return the locationX
     */
    public float getLocationX() {
        return locationX;
    }

    /**
     * @param locationX the locationX to set
     */
    public void setLocationX(float locationX) {
        this.locationX = locationX;
    }

    /**
     * @return the locationY
     */
    public float getLocationY() {
        return locationY;
    }

    /**
     * @param locationY the locationY to set
     */
    public void setLocationY(float locationY) {
        this.locationY = locationY;
    }

    /**
     * @return the scale
     */
    public int getScale() {
        return scale;
    }

    /**
     * @param scale the scale to set
     */
    public void setScale(int scale) {
        this.scale = scale;
    }

    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @param label the label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }

}
