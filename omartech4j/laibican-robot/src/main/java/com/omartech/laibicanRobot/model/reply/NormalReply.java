package com.omartech.laibicanRobot.model.reply;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * Created by guo on 14-8-22.
 */
public class NormalReply extends ReplyMessage {

    private String content;

    public String getType() {
        return MESSAGE_TYPE_TEXT;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toXML() {
        Document doc = DocumentHelper.createDocument();
        Element root = doc.addElement("xml");
        Element fromElement = root.addElement(XML_PROPERTIES_MSG_FROM);
        fromElement.addCDATA(fromName);
        Element toElement = root.addElement(XML_PROPERTIES_MSG_TO);
        toElement.addCDATA(toName);
        Element typeElement = root.addElement(XML_PROPERTIES_MSG_TYPE);
        typeElement.addCDATA(getType());
        Element timeElement = root.addElement(XML_PROPERTIES_MSG_CREATE_TIME);
        timeElement.addText(String.valueOf(System.currentTimeMillis() / 1000));

        Element contentElement = root.addElement(XML_PROPERTIES_MSG_CONTENT);
        contentElement.addCDATA(content);
        return doc.asXML();

//        if (StringUtils.equals(MESSAGE_TYPE_TEXT, type)) {
//            Element contentElement = root.addElement(XML_PROPERTIES_MSG_CONTENT);
//            contentElement.addCDATA(content);
//            return doc.asXML();
//        } else if (StringUtils.equals(MESSAGE_TYPE_IMAGE, type)) {
//            Element contentElement = root.addElement(XML_PROPERTIES_MSG_IMAGE);
//            contentElement.addElement(XML_PROPERTIES_MSG_MEDIA_ID).addCDATA(content);
//            return doc.asXML();
//        } else if (StringUtils.equals(MESSAGE_TYPE_VOICE, type)) {
//            Element contentElement = root.addElement(XML_PROPERTIES_MSG_IMAGE);
//            contentElement.addElement(XML_PROPERTIES_MSG_VOICE).addCDATA(content);
//            return doc.asXML();
//        } else {
//            return "";
//        }
    }
}
