package com.techwolf.omartech_utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * User: ChenJie
 * Date: 25/11/14
 * Time: 10:12 AM
 */
public class DBUtils {
    static Logger logger = LoggerFactory.getLogger(DBUtils.class);

    public static boolean verifyConnection(Connection conn, String sql) {
        boolean flag = false;
        try {
            if (conn.isClosed()) {
                return false;
            } else {
                PreparedStatement psmt = conn.prepareStatement(sql);
                psmt.executeQuery();
                psmt.close();
                flag = true;
            }
        } catch (SQLException e) {
            flag = false;
            e.printStackTrace();
            logger.error("{} - {}", sql, "verify connection error");
        }
        if(!flag){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }
}
