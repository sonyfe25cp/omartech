package com.omartech.laibicanRobot.model.reply;

/**
 * Created by guo on 14-8-22.
 */
public class ArticleReplyItem {

    private int id;

    private String title;

    private String description;

    private String picUrl;

    private String url;

    private int articleReplyId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getArticleReplyId() {
        return articleReplyId;
    }

    public void setArticleReplyId(int articleReplyId) {
        this.articleReplyId = articleReplyId;
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

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
