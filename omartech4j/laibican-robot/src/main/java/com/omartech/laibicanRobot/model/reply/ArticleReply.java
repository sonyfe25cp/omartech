package com.omartech.laibicanRobot.model.reply;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by guo on 14-8-22.
 */
public class ArticleReply extends ReplyMessage {

    private List<ArticleReplyItem> articleReplyItemList = new ArrayList<>();

    private Date createdAt;

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<ArticleReplyItem> getArticleReplyItemList() {
        return articleReplyItemList;
    }

    public void setArticleReplyItemList(List<ArticleReplyItem> articleReplyItemList) {
        this.articleReplyItemList = articleReplyItemList;
    }

    public void addArticleReplyItem(ArticleReplyItem articleReplyItem){
        articleReplyItemList.add(articleReplyItem);
    }

    @Override
    public String getType() {
        return MESSAGE_TYPE_NEWS;
    }

    @Override
    public String getContent() {
        JSONArray jsonArray = new JSONArray();
        for (ArticleReplyItem weixinArticle : articleReplyItemList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(XML_PROPERTIES_MSG_TITLE, weixinArticle.getTitle());
            jsonObject.put(XML_PROPERTIES_MSG_DESCRIPTION, weixinArticle.getDescription());
            jsonObject.put(XML_PROPERTIES_MSG_PIC_URL, weixinArticle.getPicUrl());
            jsonObject.put(XML_PROPERTIES_MSG_URL, weixinArticle.getUrl());
            jsonArray.add(jsonObject);
        }
        return jsonArray.toString();
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
        typeElement.addCDATA(MESSAGE_TYPE_NEWS);

        root.addElement(XML_PROPERTIES_MSG_ARTICLE_COUNT).addText(
                String.valueOf(articleReplyItemList.size()));

        Element articlesElement = root.addElement(XML_PROPERTIES_MSG_ARTICLES);

        for (ArticleReplyItem articleReplyItem : articleReplyItemList) {
            Element itemElement = articlesElement.addElement(XML_PROPERTIES_MSG_ITEM);
            itemElement.addElement(XML_PROPERTIES_MSG_TITLE).addCDATA(articleReplyItem.getTitle());
            itemElement.addElement(XML_PROPERTIES_MSG_DESCRIPTION).addCDATA(articleReplyItem.getDescription());
            itemElement.addElement(XML_PROPERTIES_MSG_PIC_URL).addCDATA(articleReplyItem.getPicUrl());
            itemElement.addElement(XML_PROPERTIES_MSG_URL).addCDATA(articleReplyItem.getUrl());
        }

        Element timeElement = root.addElement(XML_PROPERTIES_MSG_CREATE_TIME);
        timeElement.addText(String.valueOf(System.currentTimeMillis() / 1000));

        return doc.asXML();
    }
}
