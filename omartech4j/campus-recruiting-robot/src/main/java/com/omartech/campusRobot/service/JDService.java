package com.omartech.campusRobot.service;

import com.omartech.data.gen.Job;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class JDService {

    static Logger logger = LoggerFactory.getLogger(JDService.class);

    public static List<Job> findByDate(String date, Connection connection) throws SQLException {
        List<Job> list = new ArrayList<>();
        String sql = "SELECT id, title, publishDate,createdAt, url, siteName, industry, degree, company, hrEmail, headCount, parsed, body FROM jd WHERE date(createdAt) = ? ";
        try (PreparedStatement psmt = connection.prepareStatement(sql);) {
            psmt.setString(1, date);
            try (ResultSet rs = psmt.executeQuery();) {
                while (rs.next()) {
                    Job object = new Job();
                    int id = rs.getInt("id");
                    object.setId(id);
                    String title = rs.getString("title");
                    object.setTitle(title);
                    Date publishDate = rs.getDate("publishDate");
                    if(publishDate != null) {
                        object.setPublishDate(DateFormatUtils.format(publishDate, "yyyy-MM-dd"));
                    }else{
                        object.setPublishDate(date);
                    }
                    Date createdAt = rs.getDate("createdAt");
                    object.setCreatedAt(DateFormatUtils.format(createdAt, "yyyy-MM-dd"));
                    String url = rs.getString("url");
                    object.setUrl(url);
                    String siteName = rs.getString("siteName");
                    object.setSiteName(siteName);
                    String company = rs.getString("company");
                    object.setCompany(company);
                    String industry = rs.getString("industry");
                    object.setIndustry(industry);
                    String hrEmail = rs.getString("hrEmail");
                    object.setHrEmail(hrEmail);
                    int headCount = rs.getInt("headCount");
                    object.setHeadCount(headCount);
                    boolean parsed = rs.getBoolean("parsed");
                    object.setParsed(parsed);
                    String body = rs.getString("body");
                    object.setBody(body);
                    list.add(object);
                }
            }
        }
        return list;
    }
}
