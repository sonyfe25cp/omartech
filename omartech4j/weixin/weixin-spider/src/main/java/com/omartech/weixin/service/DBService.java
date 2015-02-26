package com.omartech.weixin.service;


import cn.techwolf.data.gen.WeixinAccount;
import cn.techwolf.data.gen.WeixinPost;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: ChenJie
 * Date: 25/10/14
 * Time: 11:30 AM
 */
public class DBService {


    public static List<WeixinAccount> findWeixinAccounts(int offset, int limit, Connection connection) throws SQLException {
        String sql = "SELECT id, title, name, logo, erweima, description, weixinrenzheng, isOffical, isLive, openId FROM weixinAccount where id > ? LIMIT ? ";
        List<WeixinAccount> objects = new ArrayList<>();
        PreparedStatement psmt = connection.prepareStatement(sql);
        psmt.setInt(1, offset);
        psmt.setInt(2, limit);
        ResultSet rs = psmt.executeQuery();
        while (rs.next()) {
            WeixinAccount object = new WeixinAccount();
            int id = rs.getInt("id");
            object.id = id;
            String title = rs.getString("title");
            object.title = title;
            String name = rs.getString("name");
            object.name = name;
            String logo = rs.getString("logo");
            object.logo = logo;
            String erweima = rs.getString("erweima");
            object.erweima = erweima;
            String description = rs.getString("description");
            object.description = description;
            String weixinrenzheng = rs.getString("weixinrenzheng");
            object.weixinrenzheng = weixinrenzheng;
            boolean isOffical = rs.getBoolean("isOffical");
            object.offical = isOffical;
            boolean isLive = rs.getBoolean("isLive");
            object.live = isLive;
            String openId = rs.getString("openId");
            object.openId = openId;
            objects.add(object);
        }
        rs.close();
        psmt.close();
        return objects;
    }

    public static boolean isPostExist(WeixinPost post, Connection connection){
        WeixinPost weixinPost = findWeixinPostByUrl(post.getUrl(), connection);
        if (weixinPost == null) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean insertOrUpdateWeixinPost(WeixinPost post, Connection connection) throws SQLException {
        WeixinPost weixinPost = findWeixinPostByUrl(post.getUrl(), connection);
        if (weixinPost == null) {
            insertWeixinPost(post, connection);
            return true;
        } else {
            return false;
        }
    }

    public static void insertWeixinPost(WeixinPost object, Connection conn) throws SQLException {
        String sql = "INSERT INTO weixinPost(`title`, `headImg`, `url`, `imgLink`, `siteLink`, `content168`, `content`, `date7`, `html`, `readCount`, `voteCount`, `openId`, `createdAt`) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try (
                PreparedStatement psmt = conn.prepareStatement(sql);) {
            psmt.setString(1, object.getTitle());//title
            psmt.setString(2, object.getHeadImg());//headImg
            psmt.setString(3, object.getUrl());//url
            psmt.setString(4, object.getImgLink());//imgLink
            psmt.setString(5, object.getSiteLink());//siteLink
            psmt.setString(6, object.getContent168());//content168
            psmt.setString(7, object.getContent());//content
            psmt.setString(8, object.getDate7());//date7
            psmt.setString(9, object.getHtml());//html
            psmt.setInt(10, object.getReadCount());//readCount
            psmt.setInt(11, object.getVoteCount());//voteCount
            psmt.setString(12, object.getOpenId());
            psmt.setString(13, object.getCreatedAt());
            psmt.execute();
        }

    }


    public static boolean insertOrUpdateWeixinAccount(WeixinAccount account, Connection connection) throws SQLException {

        WeixinAccount weixinAccountByOpenId = DBService.findWeixinAccountByOpenId(account.getOpenId(), connection);
        if (weixinAccountByOpenId == null) {
            insertWeixinAccount(account, connection);
            return true;
        } else {
            return false;
        }

    }


    public static void insertWeixinAccount(WeixinAccount object, Connection conn) throws SQLException {
        String sql = "INSERT INTO weixinAccount(`title`, `name`, `logo`, `erweima`, `description`, `weixinrenzheng`, `isOffical`, `isLive`, `openId`, `createdAt`) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try (
                PreparedStatement psmt = conn.prepareStatement(sql);) {
            psmt.setString(1, object.getTitle());//title
            psmt.setString(2, object.getName());//name
            psmt.setString(3, object.getLogo());//logo
            psmt.setString(4, object.getErweima());//erweima
            psmt.setString(5, object.getDescription());//description
            psmt.setString(6, object.getWeixinrenzheng());//weixinrenzheng
            psmt.setBoolean(7, object.isOffical());//isOffical
            psmt.setBoolean(8, object.isLive());//isLive
            psmt.setString(9, object.getOpenId());//openId
            psmt.setString(10, object.getCreatedAt());
            psmt.execute();
        }
    }

    public static WeixinPost findWeixinPostByUrl(String url, Connection connection) {
        WeixinPost object = null;
        String sql = "SELECT title, headImg, url, imgLink, siteLink, content168, content, date7, html, readCount, voteCount, openId FROM weixinPost WHERE url = ? ";
        try {
            PreparedStatement psmt = connection.prepareStatement(sql);
            psmt.setString(1, url);
            ResultSet rs = psmt.executeQuery();
            if (rs.next()) {
                object = new WeixinPost();
                String title = rs.getString("title");
                object.setTitle(title);
                String headImg = rs.getString("headImg");
                object.setHeadImg(headImg);
                object.setUrl(url);
                String imgLink = rs.getString("imgLink");
                object.setImgLink(imgLink);
                String siteLink = rs.getString("siteLink");
                object.setSiteLink(siteLink);
                String content168 = rs.getString("content168");
                object.setContent168(content168);
                String content = rs.getString("content");
                object.setContent(content);
                String date7 = rs.getString("date7");
                object.setDate7(date7);
                String html = rs.getString("html");
                object.setHtml(html);
                int readCount = rs.getInt("readCount");
                object.setReadCount(readCount);
                int voteCount = rs.getInt("voteCount");
                object.setVoteCount(voteCount);
                String openId = rs.getString("openId");
                object.setOpenId(openId);
            }
            rs.close();
            psmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return object;
    }


    public static WeixinAccount findWeixinAccountByOpenId(String openId, Connection connection) {
        String sql = "SELECT title, name, logo, erweima, description, weixinrenzheng, isOffical, isLive, openId FROM weixinAccount WHERE openId = ? ";
        WeixinAccount object = null;
        try {
            PreparedStatement psmt = connection.prepareStatement(sql);
            psmt.setString(1, openId);
            ResultSet rs = psmt.executeQuery();
            if (rs.next()) {
                object = new WeixinAccount();
                String title = rs.getString("title");
                object.setTitle(title);
                String name = rs.getString("name");
                object.setName(name);
                String logo = rs.getString("logo");
                object.setLogo(logo);
                String erweima = rs.getString("erweima");
                object.setErweima(erweima);
                String description = rs.getString("description");
                object.setDescription(description);
                String weixinrenzheng = rs.getString("weixinrenzheng");
                object.setWeixinrenzheng(weixinrenzheng);
                boolean isOffical = rs.getBoolean("isOffical");
                object.setOffical(isOffical);
                boolean isLive = rs.getBoolean("isLive");
                object.setLive(isLive);
                object.setOpenId(openId);
            }
            rs.close();
            psmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return object;
    }


}
