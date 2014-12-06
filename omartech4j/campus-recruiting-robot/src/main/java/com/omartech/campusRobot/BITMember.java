package com.omartech.campusRobot;

/**
 * Created by omar on 14/12/6.
 */
public class BITMember {

    private String username;
    private String link;
    private String gender;
    private String from;
    private String regDate;
    private String email;
    private String homepage;
    private String lastLogin;
    private String postCount;
    private String memScore;//会员积分

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getPostCount() {
        return postCount;
    }

    public void setPostCount(String postCount) {
        this.postCount = postCount;
    }

    public String getMemScore() {
        return memScore;
    }

    public void setMemScore(String memScore) {
        this.memScore = memScore;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "BITMember{" +
                "username='" + username + '\'' +
                ", link='" + link + '\'' +
                ", gender='" + gender + '\'' +
                ", from='" + from + '\'' +
                ", regDate='" + regDate + '\'' +
                ", email='" + email + '\'' +
                ", homepage='" + homepage + '\'' +
                ", lastLogin='" + lastLogin + '\'' +
                ", postCount='" + postCount + '\'' +
                ", memScore='" + memScore + '\'' +
                '}';
    }
}
