package com.omartech.laibicanRobot.model.reply;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * Created by guo on 14-8-22.
 */
public class MusicReply extends ReplyMessage {

    private String title;

    private String description;

    private String musicURL;

    private String hQMusicUrl;

    private String thumbMediaId;

    @Override
    public String getType() {
        return MESSAGE_TYPE_MUSIC;
    }

    @Override
    public String getContent() {
        return "";
    }

    @Override
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
        typeElement.addCDATA(MESSAGE_TYPE_MUSIC);

        Element timeElement = root.addElement(XML_PROPERTIES_MSG_CREATE_TIME);
        timeElement.addText(String.valueOf(System.currentTimeMillis() / 1000));


        Element musicElement = root.addElement(XML_PROPERTIES_MSG_MUSIC);
        musicElement.addElement(XML_PROPERTIES_MSG_TITLE).addCDATA(title);
        musicElement.addElement(XML_PROPERTIES_MSG_DESCRIPTION).addCDATA(description);
        musicElement.addElement(XML_PROPERTIES_MSG_MUSIC_URL).addCDATA(musicURL);
        musicElement.addElement(XML_PROPERTIES_MSG_HQMUSIC_URL).addCDATA(hQMusicUrl);
        musicElement.addElement(XML_PROPERTIES_MSG_THUMB_MEDIA_ID).addCDATA(thumbMediaId);

        return doc.asXML();
    }
}
