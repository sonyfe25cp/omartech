package com.omartech.review_quality.chinese;

import com.omartech.review_quality.chinese.model.KCompany;
import com.omartech.review_quality.chinese.model.KReview;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by OmarTech on 15-3-24.
 */
public class DBService {

    public static List<KCompany> findKCompany(Connection conn, int offset, int limit) throws SQLException {
        List<KCompany> companies = new ArrayList<>();
        String sql = "SELECT company_id, name, count(1) count FROM reviews_task GROUP BY company_id ORDER BY count DESC LIMIT ?,?";
        try (PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setInt(1, offset);
            psmt.setInt(2, limit);
            try (ResultSet resultSet = psmt.executeQuery();) {
                while (resultSet.next()) {
                    KCompany company = new KCompany();
                    int company_id = resultSet.getInt("company_id");
                    String name = resultSet.getString("name");
                    int count = resultSet.getInt("count");
                    company.setCompany_id(company_id);
                    company.setName(name);
                    company.setCount(count);
                    companies.add(company);
                }
            }
        }
        return companies;
    }

    public static List<KCompany> findKCompanyOrderByCompanyId(Connection conn, int offset, int limit) throws SQLException {
        List<KCompany> companies = new ArrayList<>();
        String sql = "SELECT company_id, name, count(1) count FROM reviews_task GROUP BY company_id ORDER BY company_id ASC LIMIT ?,?";
        try (PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setInt(1, offset);
            psmt.setInt(2, limit);
            try (ResultSet resultSet = psmt.executeQuery();) {
                while (resultSet.next()) {
                    KCompany company = new KCompany();
                    int company_id = resultSet.getInt("company_id");
                    String name = resultSet.getString("name");
                    int count = resultSet.getInt("count");
                    company.setCompany_id(company_id);
                    company.setName(name);
                    company.setCount(count);
                    companies.add(company);
                }
            }
        }
        return companies;
    }

    public static List<KReview> findKReviewsByCompanyId(Connection conn, int company_id, int offset, int limit) throws SQLException {
        String sql = "SELECT id, company_id, name, pros, cons, advice, rating, useful_num, weight, employee_status, recommend_friend FROM reviews_task WHERE company_id = ? LIMIT ?, ? ";
        List<KReview> reviews = new ArrayList<>();
        try {
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setInt(1, company_id);
            psmt.setInt(2, offset);
            psmt.setInt(3, limit);
            ResultSet rs = psmt.executeQuery();
            while (rs.next()) {
                KReview object = new KReview();
                int id = rs.getInt("id");
                object.setId(id);
                object.setCompany_id(company_id);
                String name = rs.getString("name");
                object.setName(name);
                String pros = rs.getString("pros");
                object.setPros(pros);
                String cons = rs.getString("cons");
                object.setCons(cons);
                String advices = rs.getString("advice");
                object.setAdvice(advices);
                int rating = rs.getInt("rating");
                object.setRating(rating);
                int useful_num = rs.getInt("useful_num");
                object.setUseful_num(useful_num);
                int weight = rs.getInt("weight");
                object.setWeight(weight);
                int recommend_friend = rs.getInt("recommend_friend");
                object.setRecommend_friend(recommend_friend);
                int employee_status = rs.getInt("employee_status");
                object.setEmployee_status(employee_status);
                reviews.add(object);
            }
            rs.close();
            psmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }
}
