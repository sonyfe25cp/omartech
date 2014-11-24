package com.omartech.laibicanRobot.model;

/**
 * Created by omar on 14-11-22.
 */
public class Article {

    private int id;
    private String articleId;//
    private String title;
    private String content;
    private String textLink;
    private String createdAt;
    private String articleType;

    public static String Joke = "joke";
    public static String Bican = "bican";

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTextLink() {
        return textLink;
    }

    public void setTextLink(String textLink) {
        this.textLink = textLink;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
