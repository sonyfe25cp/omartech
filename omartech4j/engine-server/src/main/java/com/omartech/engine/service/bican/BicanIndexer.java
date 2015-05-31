package com.omartech.engine.service.bican;

import com.omartech.engine.service.AIndexBuilder;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by omar on 15-1-4.
 */
public class BicanIndexer extends AIndexBuilder {


    static ThreadLocal<Connection> con = new InheritableThreadLocal<Connection>() {
        @Override
        protected Connection initialValue() {
            Connection conn = null;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/weixin", "root", "");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return conn;
        }
    };

    public BicanIndexer(String indexPath) throws IOException {
        super(indexPath);
    }

    public static void main(String[] args) {
        try {
            String index = "bican";
            BicanIndexer bicanIndexer = new BicanIndexer(index);
            String sql = "select id, title, content, appName, hot from article where id > ? limit ?";
            Connection connection = con.get();
            bicanIndexer.build(sql, connection);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
