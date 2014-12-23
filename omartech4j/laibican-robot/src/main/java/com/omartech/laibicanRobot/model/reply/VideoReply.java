package com.omartech.laibicanRobot.model.reply;

import net.sf.json.JSONObject;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * Created by guo on 14-8-22.
 */
public class VideoReply extends ReplyMessage {

    private String mediaId;

    private String title;

    private String description;

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getType() {
        return MESSAGE_TYPE_VIDEO;
    }

    @Override
    public String getContent() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(XML_PROPERTIES_MSG_MEDIA_ID, mediaId);
        jsonObject.put(XML_PROPERTIES_MSG_TITLE, title);
        jsonObject.put(XML_PROPERTIES_MSG_DESCRIPTION, description);
        return jsonObject.toString();
    }

    @Override
    @Deprecated
    public void setContent(String content) {

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
        typeElement.addCDATA(MESSAGE_TYPE_VIDEO);

        Element timeElement = root.addElement(XML_PROPERTIES_MSG_CREATE_TIME);
        timeElement.addText(String.valueOf(System.currentTimeMillis() / 1000));


        Element videoElement = root.addElement(XML_PROPERTIES_MSG_VIDEO);
        videoElement.addElement(XML_PROPERTIES_MSG_MEDIA_ID).addCDATA(mediaId);
        videoElement.addElement(XML_PROPERTIES_MSG_TITLE).addCDATA(title);
        videoElement.addElement(XML_PROPERTIES_MSG_DESCRIPTION).addCDATA(description);

        return doc.asXML();
    }
}
