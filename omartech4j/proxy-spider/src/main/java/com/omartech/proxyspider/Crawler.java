package com.omartech.proxyspider;

import com.omartech.proxyspider.model.HtmlObject;
import com.omartech.proxyspider.model.Proxy;
import com.omartech.proxyspider.parser.*;
import com.omartech.proxyspider.service.DBService;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
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

    //        seeds.add("http://www.ip-adress.com/proxy_list/?k=time&d=desc");
    static {
        seeds.add("http://www.idcloak.com/proxylist/free-proxy-ip-list.html#sort");
        seeds.add("http://www.idcloak.com/proxylist/anonymous-proxy-list.html");
        seeds.add("http://www.xici.net.co/");
        seeds.add("http://www.proxy360.cn/Proxy");
        seeds.add("http://www.kuaidaili.com/free/outtr/");
        seeds.add("http://www.kuaidaili.com/free/outha/");
        seeds.add("http://www.kuaidaili.com/free/intr/");
        seeds.add("http://www.kuaidaili.com/free/inha/");
        seeds.add("http://www.ultraproxies.com/");
        seeds.add("http://www.proxy.com.ru/list_1.html");
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
        parsers.add(new ProxyRuParser());

    }

    void gogo() {
        List<Proxy> totalProxies = new ArrayList<>();
        for (String seed : seeds) {
            HttpClientBuilder create = HttpClientBuilder.create();
            CloseableHttpClient client = create.build();
            HtmlObject htmlObject = Spider.fetchPage(client, seed, headers, null);
            if (htmlObject != null) {
                String html = htmlObject.getHtml();
//            logger.info(html);
                for (ProxyParser parser : parsers) {
                    if (parser.match(seed)) {
                        List<Proxy> proxies = parser.parse(html);
                        totalProxies.addAll(proxies);
                    }
                }
            }
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        int count = 0;
        try (Connection conn = con.get();) {
            for (Proxy proxy : totalProxies) {

                long lantency = validate(new HttpHost(proxy.getHost(), proxy.getPort()));
                proxy.setLatency(lantency);
                boolean flag = DBService.inserOrUpdate(proxy, conn);
                if (flag) {
                    count++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        logger.info("proxies size : {}, new:{} to db", totalProxies.size(), count);
    }

    //return latency
    public static long validate(HttpHost proxy) {
        HttpClientBuilder create = HttpClientBuilder.create();
        CloseableHttpClient client = create.build();
        int Max = 999999;
        long start = System.currentTimeMillis();
        String url = "http://www.baidu.com/";
        try {
            HtmlObject htmlObject = Spider.fetchPage(client, url, proxy, null, null);
            client.close();
            String html = htmlObject.getHtml();
            if (htmlObject == null || StringUtils.isEmpty(html)) {
                logger.info("cant reach ");
                return Max;
            } else {
                Document document = Jsoup.parse(html);
                document.select("script, style, link").remove();
                String tmp = document.toString();
                long lantency = System.currentTimeMillis() - start;
                logger.info("{} over, {} length, take {}ms, proxy:{}", new String[]{url, tmp.length() + "", lantency + "", proxy.toHostString()});
                return lantency;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("error : {}", e.getMessage());
            return Max;
        }
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
