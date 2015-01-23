package com.omartech.proxyspider.model;

/**
 * Created by omar on 15-1-17.
 */
public class WeixinPost {
    private String title;
    private String headImg;
    private String url;
    private String imgLink;
    private String siteLink;
    private String content168;
    private String content;
    private String date7;//yyyy-M-dd
    private String html;
    private int readCount;//阅读量
    private int voteCount;//点赞数
    private String openId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public String getSiteLink() {
        return siteLink;
    }

    public void setSiteLink(String siteLink) {
        this.siteLink = siteLink;
    }

    public String getContent168() {
        return content168;
    }

    public void setContent168(String content168) {
        this.content168 = content168;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate7() {
        return date7;
    }

    public void setDate7(String date7) {
        this.date7 = date7;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public int getReadCount() {
        return readCount;
    }

    public void setReadCount(int readCount) {
        this.readCount = readCount;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    @Override
    public String toString() {
        return "WeixinPost{" +
                "title='" + title + '\'' +
                ", headImg='" + headImg + '\'' +
                ", url='" + url + '\'' +
                ", imgLink='" + imgLink + '\'' +
                ", siteLink='" + siteLink + '\'' +
                ", content168='" + content168 + '\'' +
//                ", content='" + content + '\'' +
                ", date7='" + date7 + '\'' +
//                ", html='" + html + '\'' +
                ", readCount='" + readCount + '\'' +
                ", voteCount='" + voteCount + '\'' +
                '}';
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
