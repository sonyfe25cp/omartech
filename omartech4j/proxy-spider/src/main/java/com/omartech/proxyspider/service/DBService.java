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

    public static boolean inserOrUpdate(Proxy proxy, Connection conn) throws SQLException {
        String host = proxy.getHost();
        int port = proxy.getPort();
        Proxy proxy1 = findProxy(host, port, conn);
        if (proxy1 == null) {
            insert(proxy, conn);
            return true;
        } else {
            proxy1.setLatency(proxy.getLatency());
            update(proxy1, conn);
            return false;
        }
    }

    public static void update(Proxy proxy, Connection conn) throws SQLException {
        String sql = "UPDATE proxy SET latency = ? WHERE id = ?";
        try (
                PreparedStatement psmt = conn.prepareStatement(sql);) {
            psmt.setLong(1, proxy.getLatency());
            psmt.setInt(2, proxy.getId());
            psmt.executeUpdate();
            psmt.close();
        }
    }


    public static void insert(Proxy proxy, Connection conn) throws SQLException {
        String sql = "INSERT INTO proxy(host, port, proxyType, status, created_at, comment, latency) VALUES(?, ?, ?, ?, ?, ?, ?);";
        try (PreparedStatement psmt = conn.prepareStatement(sql);) {
            psmt.setString(1, proxy.host);//host
            psmt.setInt(2, proxy.port);//port
            psmt.setString(3, proxy.proxyType.toLowerCase());//proxyType
            psmt.setInt(4, proxy.status);//status
            psmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            psmt.setString(6, proxy.getComment());
            psmt.setLong(7, proxy.getLatency());
            psmt.execute();
            psmt.close();
        }
    }

    public static Proxy findProxy(String host, int port, Connection conn) throws SQLException {
        String sql = "SELECT * FROM proxy WHERE host = ? AND port = ?";
        Proxy proxy = null;
        try (PreparedStatement psmt = conn.prepareStatement(sql);) {
            psmt.setString(1, host);//host
            psmt.setInt(2, port);//port
            ResultSet rs = psmt.executeQuery();

            while (rs.next()) {
                proxy = new Proxy();
                String host1 = rs.getString("host");
                int port1 = rs.getInt("port");
                int id = rs.getInt("id");
                proxy.host = host1;
                proxy.port = port1;
                proxy.id = id;
            }
            rs.close();
        }
        return proxy;
    }


    public static List<Proxy> findProxyList(int size, String proxyType, Connection conn) throws SQLException {
        String sql = "SELECT * FROM proxy WHERE proxyType = ? ORDER BY id DESC LIMIT ? ";
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
        }
        return proxies;
    }


}
