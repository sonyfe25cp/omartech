package com.omartech.proxyspider.service;

import com.omartech.proxyspider.model.Proxy;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * User: ChenJie
 * Date: 25/10/14
 * Time: 11:30 AM
 */
public class DBService {

    public static boolean inserOrNot(Proxy proxy, Connection conn) {
        String host = proxy.getHost();
        int port = proxy.getPort();
        Proxy proxy1 = findProxy(host, port, conn);
        if (proxy1 == null) {
            insert(proxy, conn);
            return true;
        } else {
            return false;
        }
    }

    public static void insert(Proxy proxy, Connection conn) {
        String sql = "insert into proxy(host, port, proxyType, status, created_at) values(?, ?, ?, ?, ?);";
        try {
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setString(1, proxy.host);//host
            psmt.setInt(2, proxy.port);//port
            psmt.setString(3, proxy.proxyType);//proxyType
            psmt.setInt(4, proxy.status);//status
            psmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            psmt.execute();
            psmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Proxy findProxy(String host, int port, Connection conn) {
        String sql = "select * from proxy where host = ? and port = ?";
        Proxy proxy = null;
        try {
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setString(1, host);//host
            psmt.setInt(2, port);//port
            ResultSet rs = psmt.executeQuery();

            while (rs.next()) {
                proxy = new Proxy();
                String host1 = rs.getString("host");
                int port1 = rs.getInt("port");
                proxy.host = host1;
                proxy.port = port1;
            }
            rs.close();
            psmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return proxy;
    }


    public static List<Proxy> findProxyList(int size, String proxyType, Connection conn) {
        String sql = "select * from proxy where proxyType = ? order by id desc limit ? ";
        List<Proxy> proxies = new ArrayList<>();
        try (PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setString(1, proxyType);
            psmt.setInt(2, size);
            try (ResultSet rs = psmt.executeQuery();) {

                while (rs.next()) {
                    Proxy proxy = new Proxy();
                    String host1 = rs.getString("host");
                    int port1 = rs.getInt("port");
                    proxy.host = host1;
                    proxy.port = port1;

                    proxies.add(proxy);

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return proxies;
    }


//    Connection conn = null;
//    public void initDB() {
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/proxy", "root", "");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//    public void close() {
//        try {
//            conn.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                conn.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }

}
