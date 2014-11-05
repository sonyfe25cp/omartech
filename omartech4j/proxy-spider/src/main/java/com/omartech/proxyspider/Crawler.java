package com.omartech.proxyspider;

import com.omartech.proxyspider.model.HtmlObject;
import com.omartech.proxyspider.model.Proxy;
import com.omartech.proxyspider.parser.*;
import com.omartech.proxyspider.service.DBService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: ChenJie
 * Date: 25/10/14
 * Time: 10:58 AM
 */
public class Crawler {
    static Logger logger = LoggerFactory.getLogger(Crawler.class);

    public static void main(String[] args) {
        Crawler crawler = new Crawler();
        crawler.gogo();
    }

    static List<String> seeds = new ArrayList<>();
    static Map<String, String> headers = new HashMap<>();
    static List<ProxyParser> parsers = new ArrayList<>();

    static {
        seeds.add("http://www.idcloak.com/proxylist/free-proxy-ip-list.html#sort");
        seeds.add("http://www.idcloak.com/proxylist/anonymous-proxy-list.html");
        seeds.add("http://www.ip-adress.com/proxy_list/?k=time&d=desc");
        seeds.add("http://www.xici.net.co/");
        seeds.add("http://www.proxy360.cn/Proxy");
        seeds.add("http://www.kuaidaili.com/free/outtr/");
        seeds.add("http://www.kuaidaili.com/free/outha/");
        seeds.add("http://www.kuaidaili.com/free/intr/");
        seeds.add("http://www.kuaidaili.com/free/inha/");
//        seeds.add("http://www.ultraproxies.com/");
        headers.put("Accept",
                "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        headers.put("Accept-Encoding", "gzip,deflate,sdch");
        headers.put("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.6");
        headers.put("Cache-Control", "no-cache");
        headers.put("Connection", "keep-alive");
        headers.put("Pragma", "no-cache");
        headers.put(
                "User-Agent",
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.122 Safari/537.36");

        parsers.add(new IdcloakParser());
        parsers.add(new IPAddressParser());
        parsers.add(new XiciParser());
        parsers.add(new UltraproxiesParser());
        parsers.add(new Proxy360Parser());
        parsers.add(new KuaidailiParser());

    }

    void gogo() {
        List<Proxy> totalProxies = new ArrayList<>();
        for (String seed : seeds) {
            HtmlObject htmlObject = Spider.fetchPage(seed, headers, null);

            String html = htmlObject.getHtml();
//            logger.info(html);
            for (ProxyParser parser : parsers) {
                if (parser.match(seed)) {
                    List<Proxy> proxies = parser.parse(html);
                    totalProxies.addAll(proxies);
                }
            }
        }
        int count = 0;
        try (Connection conn = con.get();) {
            for (Proxy proxy : totalProxies) {
                logger.info(proxy.toString());
                boolean flag = DBService.inserOrNot(proxy, conn);
                if (flag) {
                    count++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        logger.info("proxies size : {}, insert {} proxies to db", totalProxies.size(), count);
    }

    static ThreadLocal<Connection> con = new InheritableThreadLocal<Connection>() {
        @Override
        protected Connection initialValue() {
            Connection conn = null;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/proxy", "root", "");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return conn;
        }
    };

}
