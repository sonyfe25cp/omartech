package com.omartech.review_quality.service;

import com.omartech.review_quality.model.Product;
import com.omartech.review_quality.model.Review;
import com.omartech.review_quality.model.Reviewer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by OmarTech on 15-1-26.
 */
public class DataService {

    public static void insertProduct(Product object, Connection conn) throws SQLException {
        String sql = "INSERT INTO product(`productId`, `name`, `type`, `brand`, `salesPrice`, `listPrice`, `categoryPath`, `description`) VALUES(?, ?, ?, ?, ?, ?, ?,?);";
        try (
                PreparedStatement psmt = conn.prepareStatement(sql);) {
            psmt.setString(1, object.getProductId());//productId
            psmt.setString(2, object.getName());//name
            psmt.setString(3, object.getType());//type
            psmt.setString(4, object.getBrand());//brand
            psmt.setString(5, object.getSalesPrice());//salesPrice
            psmt.setString(6, object.getListPrice());//listPrice
            psmt.setString(7, object.getCategoryPath());//categoryPath
            psmt.setString(8, object.getDescription());//description
            psmt.execute();
        }

    }

    public static void insertReview(Review object, Connection conn) throws SQLException {
        String sql = "INSERT INTO review(`productId`, `reviewerId`, `createdAt`, `helpfulCount`, `feedbackCount`, `rating`, `title`, `body`) VALUES(?, ?, ?, ?, ?, ?, ?, ?);";
        try (
                PreparedStatement psmt = conn.prepareStatement(sql);) {
            psmt.setString(1, object.getProductId());//productId
            psmt.setString(2, object.getReviewerId());//reviewerId
            psmt.setString(3, object.getCreatedAt());//createdAt
            psmt.setInt(4, object.getHelpfulCount());//helpfulCount
            psmt.setInt(5, object.getFeedbackCount());//feedbackCount
            psmt.setInt(6, object.getRating());//rating
            psmt.setString(7, object.getTitle());//title
            psmt.setString(8, object.getBody());//body
            psmt.execute();
        }


    }

    public static void insertReviewer(Reviewer object, Connection conn) throws SQLException {
        String sql = "INSERT INTO reviewer(`reviewerId`, `name`, `reviews`, `summary`) VALUES(?, ?, ?, ?);";
        try (
                PreparedStatement psmt = conn.prepareStatement(sql);) {
            psmt.setString(1, object.getReviewerId());//reviewerId
            psmt.setString(2, object.getName());//name
            psmt.setInt(3, object.getReviews());//reviews
            psmt.setString(4, object.getSummary());//summary
            psmt.execute();
        }
    }

}
