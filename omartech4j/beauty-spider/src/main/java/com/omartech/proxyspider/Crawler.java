package com.omartech.proxyspider;

import com.google.gson.Gson;
import com.omartech.proxyspider.model.BeautyModel;
import com.omartech.proxyspider.model.HtmlObject;
import com.omartech.proxyspider.model.Image;
import com.omartech.proxyspider.service.DBService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

/**
 * User: ChenJie
 * Date: 25/10/14
 * Time: 10:58 AM
 */
public class Crawler {
    static Logger logger = LoggerFactory.getLogger(Crawler.class);

    public static void main(String[] args) throws SQLException {
        Crawler crawler = new Crawler();
        crawler.gogo();
    }

    static Map<String, String> headers = new HashMap<>();

    static {
        headers.put("Accept", "*/*");
        headers.put("Accept-Encoding", "gzip, deflate, sdch");
        headers.put("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.6");
        headers.put("Cache-Control", "no-cache");
        headers.put("Connection", "keep-alive");
        headers.put("Pragma", "no-cache");
        headers.put(
                "User-Agent",
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36");
        headers.put("Cookie", "BAIDUID=74163DBECDB9F88994C250424835E76B:FG=1; Hm_lvt_737dbb498415dd39d8abf5bc2404b290=1419919998; Hm_lpvt_737dbb498415dd39d8abf5bc2404b290=1419919998");
        headers.put("Host", "image.baidu.com");
        headers.put("refer", "http://image.baidu.com/channel?c=%E7%BE%8E%E5%A5%B3");
    }

    static Set<String> seeds = new HashSet<>();

    static {
        String getUrl = "http://image.baidu.com/data/imgs?col=%E7%BE%8E%E5%A5%B3&tag=%E5%85%A8%E9%83%A8&sort=0&tag3=&rn=60&p=channel&from=1&pn=";
        String sexUrl = "http://image.baidu.com/data/imgs?col=%E7%BE%8E%E5%A5%B3&tag=%E6%80%A7%E6%84%9F&sort=0&tag3=&rn=60&p=channel&from=1&pn=";
        seeds.add(getUrl);
        seeds.add(sexUrl);
    }

    void gogo() throws SQLException {

        List<Image> list = new ArrayList();
        Gson gson = new Gson();
        for (String seed : seeds) {

            int max = 0;
            int pn = 0;
            int right = 0;
            do {
                String tmpUrl = seed + pn;
                HtmlObject htmlObject = Spider.fetchPage(tmpUrl, headers, null);
                if (htmlObject != null) {
                    String html = htmlObject.getHtml();
                    BeautyModel beautyModel = gson.fromJson(html, BeautyModel.class);
                    max = beautyModel.getTotalNum();
                    list = beautyModel.getImgs();
                    for (Image image : list) {
                        try {
                            boolean flag = DBService.inserOrNot(image, con.get());
                            if (flag) {
                                logger.info("url : {}", image.getDownloadUrl());
                                right++;
                            }
                        } catch (Exception e) {
                            System.out.println(image);
                            e.printStackTrace();
                            continue;
                        }
                    }
                } else {

                }
                pn = pn + 60;
//            break;
            } while (right > 2 && pn < max);
        }
    }

    static ThreadLocal<Connection> con = new InheritableThreadLocal<Connection>() {
        @Override
        protected Connection initialValue() {
            Connection conn = null;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/beauty", "root", "");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return conn;
        }
    };

}
