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
 * Comments for WeixinLinkMessage.java
 * 
 * @author <a href="mailto:liujun@techwolf.cn">刘军</a>
 * @createTime 2014年8月14日 下午3:29:56
 */
public class WeixinLinkMessage extends WeixinMessage {

    /**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(WeixinArticleMessage.class);

    private String title;

    private String description;

    private String url;

    /* (non-Javadoc)
     * @see cn.techwolf.kanzhun.weixin.mp.model.WeixinMessage#getType()
     */
    @Override
    public String getType() {
        return MESSAGE_TYPE_LINK;
    }

    /* (non-Javadoc)
     * @see cn.techwolf.kanzhun.weixin.mp.model.WeixinMessage#getContent()
     */
    @Override
    public String getContent() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(XML_PROPERTIES_MSG_TITLE, title);
        jsonObject.put(XML_PROPERTIES_MSG_DESCRIPTION, description);
        jsonObject.put(XML_PROPERTIES_MSG_URL, url);
        return jsonObject.toString();
    }

    /* (non-Javadoc)
     * @see cn.techwolf.kanzhun.weixin.mp.model.WeixinMessage#setContent(java.lang.String)
     */
    @Override
    public void setContent(String content) {
        try {
            JSONObject jsonObject = JSONObject.fromObject(content);
            url = jsonObject.optString(XML_PROPERTIES_MSG_URL);
            title = jsonObject.optString(XML_PROPERTIES_MSG_TITLE);
            description = jsonObject.optString(XML_PROPERTIES_MSG_DESCRIPTION);
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

        Element titleElement = root.addElement(XML_PROPERTIES_MSG_TITLE);
        titleElement.addCDATA(title);

        Element descriptionElement = root.addElement(XML_PROPERTIES_MSG_DESCRIPTION);
        descriptionElement.addCDATA(description);

        Element urlElement = root.addElement(XML_PROPERTIES_MSG_URL);
        urlElement.addCDATA(url);

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
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

}
