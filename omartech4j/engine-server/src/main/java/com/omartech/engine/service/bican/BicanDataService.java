package com.omartech.engine.service.bican;

import com.omartech.data.gen.Article;
import com.omartech.data.gen.ArticleType;
import com.omartech.engine.service.AIndexService;
import org.apache.commons.lang3.StringUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BicanDataService {

    public static void insert(Article object, Connection conn) throws SQLException {
        String sql = "INSERT INTO article(`title`, `content`, `createdAt`, `appName`) VALUES(?, ?, ?, ?);";
        try (
                PreparedStatement psmt = conn.prepareStatement(sql);) {
            psmt.setString(1, object.getTitle());//title
            psmt.setString(2, object.getContent());//content
            String createdAt = object.getCreatedAt();
            Date date = AIndexService.parseTime(createdAt);
            Timestamp tm = new Timestamp(date.getTime());
            psmt.setTimestamp(3, tm);//createdAt
            psmt.setString(4, object.getArticleType().toString());//appName
            psmt.execute();
        }
    }

    public static Article findById(long id, Connection connection) {
        String sql = "SELECT title, content, createdAt, appName, hot FROM article WHERE id = ? ";
        Article object = null;
        try {
            PreparedStatement psmt = connection.prepareStatement(sql);
            psmt.setLong(1, id);
            ResultSet rs = psmt.executeQuery();
            if (rs.next()) {
                object = new Article();
                String title = rs.getString("title");
                object.setTitle(title);
                String content = rs.getString("content");
                if (!StringUtils.isEmpty(content)) {
                    content = content.replaceAll("\\[img\\].*?\\[/img\\]", "");
                    content = content.replaceAll("\\[url\\]", "<a style='color:blue' target='blank' href='");
                    content = content.replaceAll("\\[/url\\]", "'>外部链接</a>");
                }
                object.setContent(content);
                Date createdAt = rs.getDate("createdAt");
                object.setCreatedAt(AIndexService.formatTime(createdAt));
                String appName = rs.getString("appName");
                ArticleType articleType = ArticleType.valueOf(appName);
                object.setArticleType(articleType);
                int hot = rs.getInt("hot");
                object.setHot(hot);
                object.setId(id);


            }
            rs.close();
            psmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return object;
    }

    public static void updateArticleHot(long id, int hot, Connection connection) throws SQLException {
        String sql = "UPDATE article SET hot = ? WHERE id = ?";
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, hot);
            psmt.setLong(2, id);
            psmt.executeUpdate();
        }
    }


    public static List<Article> findArticles(int offset, int limit, Connection connection) {
        String sql = "SELECT id, title, content, createdAt, appName FROM article ORDER BY id DESC LIMIT ?,? ";
        List<Article> list = new ArrayList<>();
        try {
            PreparedStatement psmt = connection.prepareStatement(sql);
            psmt.setInt(1, offset);
            psmt.setInt(2, limit);
            ResultSet rs = psmt.executeQuery();
            while (rs.next()) {
                Article object = new Article();
                long id = rs.getLong("id");
                object.setId(id);
                String title = rs.getString("title");
                object.setTitle(title);
                String content = rs.getString("content");
                object.setContent(content);
                Date createdAt = rs.getDate("createdAt");
                object.setCreatedAt(AIndexService.formatTime(createdAt));
                String appName = rs.getString("appName");
                ArticleType articleType = ArticleType.valueOf(appName);
                object.setArticleType(articleType);
                int hot = rs.getInt("hot");
                object.setHot(hot);
                list.add(object);
            }
            rs.close();
            psmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}