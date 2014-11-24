/**
 * techwolf.cn All rights reserved.
 */
package com.omartech.laibicanRobot.model.message;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Comments for WeixinArticleMesssage.java
 * 
 * @author <a href="mailto:liujun@techwolf.cn">刘军</a>
 * @createTime 2014年8月14日 下午5:07:40
 */
public class WeixinArticleMessage extends WeixinMessage {

    /**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(WeixinArticleMessage.class);

    private List<WeixinArticle> articleList;

    /* (non-Javadoc)
     * @see cn.techwolf.kanzhun.weixin.mp.model.WeixinMessage#getType()
     */
    @Override
    public String getType() {
        return MESSAGE_TYPE_NEWS;
    }

    /* (non-Javadoc)
     * @see cn.techwolf.kanzhun.weixin.mp.model.WeixinMessage#getContent()
     */
    @Override
    public String getContent() {
        JSONArray jsonArray = new JSONArray();
        for (WeixinArticle weixinArticle : articleList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(XML_PROPERTIES_MSG_TITLE, weixinArticle.getTitle());
            jsonObject.put(XML_PROPERTIES_MSG_DESCRIPTION, weixinArticle.getDescription());
            jsonObject.put(XML_PROPERTIES_MSG_PIC_URL, weixinArticle.getPicUrl());
            jsonObject.put(XML_PROPERTIES_MSG_URL, weixinArticle.getUrl());
            jsonArray.add(jsonObject);
        }

        return jsonArray.toString();
    }

    /* (non-Javadoc)
     * @see cn.techwolf.kanzhun.weixin.mp.model.WeixinMessage#setContent(java.lang.String)
     */
    @Override
    public void setContent(String content) {
        try {
            List<WeixinArticle> articleList = new ArrayList<WeixinArticle>();
            JSONArray jsonArray = JSONArray.fromObject(content);
            for (int i = 0; i < jsonArray.size(); i++) {
                WeixinArticle weixinArticle = new WeixinArticle();
                JSONObject jsonObject = jsonArray.optJSONObject(i);
                weixinArticle.setTitle(jsonObject.optString(XML_PROPERTIES_MSG_TITLE));
                weixinArticle.setDescription(jsonObject.optString(XML_PROPERTIES_MSG_DESCRIPTION));
                weixinArticle.setPicUrl(jsonObject.optString(XML_PROPERTIES_MSG_PIC_URL));
                weixinArticle.setUrl(jsonObject.optString(XML_PROPERTIES_MSG_URL));
                articleList.add(weixinArticle);
            }
            this.articleList = articleList;
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

        root.addElement(XML_PROPERTIES_MSG_ARTICLE_COUNT).addText(
                String.valueOf(articleList.size()));

        Element articlesElement = root.addElement(XML_PROPERTIES_MSG_ARTICLES);

        for (WeixinArticle weixinArticle : articleList) {
            Element itemElement = articlesElement.addElement(XML_PROPERTIES_MSG_ITEM);
            itemElement.addElement(XML_PROPERTIES_MSG_TITLE).addCDATA(weixinArticle.getTitle());
            itemElement.addElement(XML_PROPERTIES_MSG_DESCRIPTION).addCDATA(weixinArticle.getDescription());
            itemElement.addElement(XML_PROPERTIES_MSG_PIC_URL).addCDATA(weixinArticle.getPicUrl());
            itemElement.addElement(XML_PROPERTIES_MSG_URL).addCDATA(weixinArticle.getUrl());
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
        return "";
    }

    /**
     * @return the articleList
     */
    public List<WeixinArticle> getArticleList() {
        return articleList;
    }

    /**
     * @param articleList the articleList to set
     */
    public void setArticleList(List<WeixinArticle> articleList) {
        this.articleList = articleList;
    }

}
