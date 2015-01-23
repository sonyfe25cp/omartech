package com.omartech.proxyspider.model;

/**
 * Created by omar on 15-1-17.
 */
public class WeixinAccount {
    private  String title;//微信名
    private String name;//微信号
    private String logo;
    private String erweima;//二维码
    private String description;
    private String weixinrenzheng;//认证描述
    private boolean isOffical;//认证
    private boolean isLive;//有没文章
    private String openId;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getErweima() {
        return erweima;
    }

    public void setErweima(String erweima) {
        this.erweima = erweima;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWeixinrenzheng() {
        return weixinrenzheng;
    }

    public void setWeixinrenzheng(String weixinrenzheng) {
        this.weixinrenzheng = weixinrenzheng;
    }

    public boolean isOffical() {
        return isOffical;
    }

    public void setOffical(boolean isOffical) {
        this.isOffical = isOffical;
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean isLive) {
        this.isLive = isLive;
    }

    @Override
    public String toString() {
        return "WeixinAccount{" +
                "title='" + title + '\'' +
                ", name='" + name + '\'' +
                ", logo='" + logo + '\'' +
                ", erweima='" + erweima + '\'' +
                ", description='" + description + '\'' +
                ", weixinrenzheng='" + weixinrenzheng + '\'' +
                ", isOffical=" + isOffical +
                ", isLive=" + isLive +
                '}';
    }
}
