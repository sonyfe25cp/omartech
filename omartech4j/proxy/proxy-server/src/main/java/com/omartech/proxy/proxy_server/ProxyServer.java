package com.omartech.proxy.proxy_server;

import com.google.gson.Gson;
import com.omartech.data.gen.Proxy;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by OmarTech on 15-1-24.
 */
public class ProxyServer extends AbstractHandler {
    static Logger logger = LoggerFactory.getLogger(ProxyServer.class);

    public static void main(String[] args) {

        Server server = new Server(7878);
        ContextHandler context = new ContextHandler();
        context.setContextPath("/");
        context.setResourceBase(".");
        context.setClassLoader(Thread.currentThread().getContextClassLoader());
        server.setHandler(context);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            context.setHandler(new ProxyServer());
            server.start();
            server.join();
        } catch (Exception e) {
            logger.error("cant start the server, {}", e.getMessage());
        }
    }

    static Gson gson = new Gson();

    @Override
    public void handle(String path,
                       Request request,
                       HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse) throws IOException, ServletException {
        logger.info("path: {}", path);
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        request.setHandled(true);
        String substring = path.substring(1);
        if (StringUtils.isEmpty(substring) || substring.equals("favicon.ico")) {
            httpServletResponse.getWriter().println("Hi, man. If you want to use this, contact coder.omar@gmail.com");
        } else {
            try {
                int pageNo = Integer.parseInt(substring);//数字
                pageNo = pageNo > 0 ? pageNo : 1;
                int pageSize = 300;
                int offset = (pageNo - 1) * pageSize;
                httpServletResponse.setContentType("application/json;charset=utf-8");
                long start = System.currentTimeMillis();
                String ipAddr = getIpAddr(httpServletRequest);
                try {

                    try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/proxy", "root", "");) {
                        List<Proxy> http = findProxyList(offset, pageSize, connection);
                        String json = gson.toJson(http);
                        long end = System.currentTimeMillis();
                        logger.info("{} req proxy page: {}, list size: {}, used {}ms", new String[]{ipAddr, pageNo + "", http.size() + "", (end - start) + ""});
                        httpServletResponse.getWriter().println(json);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    logger.error("cant connect the db", e.getMessage());
                }

            } catch (Exception e) {
                //非数字
                httpServletResponse.getWriter().println("Hi, man. If you want to use this, contact coder.omar@gmail.com");
            }
        }


    }

    public static List<Proxy> findProxyList(int offset, int limit, Connection conn) throws SQLException {
        String sql = "SELECT * FROM proxy WHERE latency > 0 ORDER BY latency ASC LIMIT ?,? ";
        List<Proxy> proxies = new ArrayList<>();
        try (PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setInt(1, offset);
            psmt.setInt(2, limit);
            try (ResultSet rs = psmt.executeQuery();) {
                while (rs.next()) {
                    Proxy proxy = new Proxy();
                    String host1 = rs.getString("host");
                    int port1 = rs.getInt("port");
                    proxy.host = host1;
                    proxy.port = port1;
                    int id = rs.getInt("id");
                    proxy.setId(id);
                    long latency = rs.getLong("latency");
                    proxy.setLatency(latency);
                    proxies.add(proxy);

                }
            }
        }
        return proxies;
    }

    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
