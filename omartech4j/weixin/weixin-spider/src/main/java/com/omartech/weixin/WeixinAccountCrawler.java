package com.omartech.weixin;

import cn.techwolf.data.gen.HtmlObject;
import cn.techwolf.data.gen.WeixinAccount;
import com.google.gson.Gson;
import com.omartech.weixin.service.DBService;
import com.techwolf.omartech_utils.DBUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * User: ChenJie
 * Date: 25/10/14
 * Time: 10:58 AM
 */
public class WeixinAccountCrawler {
    static Logger logger = LoggerFactory.getLogger(WeixinAccountCrawler.class);

    public static void main(String[] args) {
        WeixinAccountCrawler crawler = new WeixinAccountCrawler();
        crawler.downloadAccounts();

//        createPairUID();
        readCookie();
    }


    private int cpu = Runtime.getRuntime().availableProcessors();
//    static ProxyClient proxyClient = new ProxyClient();
    String alreadCrawledFile = "/tmp/wexinalready";

    private void downloadAccounts() {

        ThreadPoolExecutor executor = new ThreadPoolExecutor(cpu, cpu, 1, TimeUnit.DAYS, new ArrayBlockingQueue<Runnable>(cpu * 2), new ThreadPoolExecutor.CallerRunsPolicy());
        Set<String> wordsList = new HashSet<>();//init search words

        Set<String> already = new HashSet<>();
        try {
            List<String> strings = FileUtils.readLines(new File(alreadCrawledFile));
            for (String tmp : strings) {
                String[] split = tmp.split(" ");
                already.add(split[0]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //version 1
//        String characters = Utils.getResouce("characters");
//        for (char ch : characters.toCharArray()) {
//            String tmp = ch + "";
//            if (!StringUtils.isEmpty(StringUtils.deleteWhitespace(tmp))) {
//                if (!already.contains(tmp)) {
//                    wordsList.add(ch + "");
//                }
//            }
//        }

        //version2
//        try {
//            List<String> strings = FileUtils.readLines(new File("/tmp/company_names"));
//            for (String tmp : strings) {
//                tmp = StringUtils.deleteWhitespace(tmp);
//                tmp = tmp.replaceAll("[\"';,.，。\\.\\|\\-\\^\\~\\+]", "");
//                tmp = tmp.replaceAll("[\\[\\(（].+[\\)）\\]]", "");
//                if (!StringUtils.isEmpty(tmp)) {
//                    List<Term> terms = ToAnalysis.parse(tmp);
//                    for(Term term : terms){
//                        String name = term.getName();
//                        if(name.length() > 1) {
//                            wordsList.add(name);
//                        }
//                    }
//                    wordsList.add(tmp);
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        //version3
        try {
            List<String> strings = FileUtils.readLines(new File("/tmp/1and2"));
            for (String tmp : strings) {
                String[] split = tmp.split("\t");
                if (split.length > 0) {
                    String s = split[0];
                    if (!already.contains(s) && (!StringUtils.isEmpty(s))) {
                        wordsList.add(s);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        logger.info("wordsList.size : {}", wordsList.size());
        for (String word : wordsList) {
            logger.info(word);
            new AccountDownloadWorker(word).run();
//            executor.submit(new AccountDownloadWorker(word));
        }
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static String UA_COMPUTER = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36";
    private static String UA_WEIXIN = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36";


    static String goodCookie = readCookie();

    private HtmlObject fetchResultsList(String word, int pageNo) {

        String refer = "http://weixin.sogou.com/";


        int ran = new Random().nextInt(1000);
        int ast = 1421510528 + ran;

        String url = "http://weixin.sogou.com/weixin?type=1&fr=sgsearch&ie=utf8&_ast=" + ast + "&_asf=null&w=01029901&p=40040100&dp=1&cid=null&query=" + word + "&page=" + pageNo;
//        String url = "http://127.0.0.1:9901/weixin?type=1&fr=sgsearch&ie=utf8&_ast=" + ast + "&_asf=null&w=01029901&p=40040100&dp=1&cid=null&query=" + word + "&page=" + pageNo;

        boolean flag = false;
        HtmlObject object = null;
        int times = 0;
        do {
//        String[] pairUID = createPairUID();
            Map<String, String> headers = new HashMap<>();
            int lclkint = new Random().nextInt(8999) + 1000;
            headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            headers.put("Accept-Encoding", "gzip, deflate, sdch");
            headers.put("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.6");
            headers.put("Cache-Control", "no-cache");
            headers.put("Connection", "keep-alive");
//            headers.put("Cookie", "IPLOC=CN1100; SUID=" + pairUID[0] + "; SUIR=1422201303; SUV=1422201301474000170; SNUID=8289BBB3C7CDCABBC3BA9B4EC8BBF412; usid=" + createUserId() + "; SUID=" + pairUID[1] + "; ABTEST=7|1422201369|v17; LSTMV=272%2C58; LCLKINT=46021; sct=2");
//            headers.put("Cookie", "SUID=4A41737B2708930A0000000054C5213C; SUIR=1422205244; IPLOC=CN1100; SUV=007A7BE47B73414A54C5213C938DF439;");
            headers.put("Cookie", goodCookie);
            headers.put("Host", "weixin.sogou.com");
            headers.put("Pragma", "no-cache");
            headers.put("Referer", refer);
            headers.put("User-Agent", UA_COMPUTER);

            HttpHost proxy = null;
//
            object = Spider.fetchPage(url, proxy, headers, refer);
            switch (object.getStatusCode()) {
                case 200:
                    flag = true;
                    break;
                default:
                    object = null;
                    goodCookie = readCookie();
                    break;
            }
            times++;
            if (times > 5) {
                logger.error("这词有问题：{}, cookie:{}", word, goodCookie);
                break;
            }
        } while (!flag);
        return object;
    }


    class AccountDownloadWorker implements Runnable {
        String word;

        public AccountDownloadWorker(String word) {
            this.word = word;
        }

        @Override
        public void run() {
            try {
                int maxPage = 10;
                for (int currentPageNo = 1; currentPageNo <= maxPage; currentPageNo++) {
                    HtmlObject listPage = fetchResultsList(word, currentPageNo);
                    if (listPage != null && listPage.getHtml() != null) {
                        Connection connection = null;
                        boolean dbflag = true;
                        do {
                            connection = con.get();
                            dbflag = DBUtils.verifyConnection(connection, "select id from weixinAccount limit 1");
                            if (!dbflag) {
                                con.remove();
                            }
                        } while (!dbflag);
                        String html = listPage.getHtml();
                        List<WeixinAccount> accounts = parseAccountFromHtml(html);
                        int alreadyHave = 0;
                        for (WeixinAccount account : accounts) {
                            logger.info("account: {}, openId:{}", account.getTitle(), account.getOpenId());
                            account.setCreatedAt(DateFormatUtils.format(new Date(), "yyyy-MM-dd"));
                            boolean flag = DBService.insertOrUpdateWeixinAccount(account, connection);
                        }
                        maxPage = parseMaxPageFromHtml(html);
                        logger.info("word: {}, maxPage : {}, currentPage:{}", new String[]{word, maxPage + "", currentPageNo + ""});
                    }
                }
                logger.info("该词抓完了..{}, 共:{}页", word, maxPage);
                FileUtils.write(new File(alreadCrawledFile), word + " " + maxPage + "\n", true);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    Gson gson = new Gson();

    private int parseMaxPageFromHtml(String html) {
        Document document = Jsoup.parse(html);
        Elements elements = document.select("#pagebar_container a");
        int max = 0;
        for (Element element : elements) {
            String text = element.text();
            try {
                int num = Integer.parseInt(text);
                max = Math.max(num, max);
            } catch (Exception e) {
            }
        }
        return max;
    }

    private List<WeixinAccount> parseAccountFromHtml(String html) {

        Document document = Jsoup.parse(html);
        Elements divs = document.select(".wx-rb.bg-blue.wx-rb_v1._item");
        List<WeixinAccount> weixinAccounts = new ArrayList<>();
        for (Element div : divs) {
            WeixinAccount weixinAccount = new WeixinAccount();

            String href = div.attr("href");
            String openId = href.substring(href.indexOf("=") + 1);
            weixinAccount.setOpenId(openId);

            Element logo = div.select(".img-box img").first();
            String logo_url = logo.attr("src");

            weixinAccount.setLogo(logo_url);

            Element textbox = div.select(".txt-box").first();
            String title = textbox.select("h3").text();
            String weixinName = textbox.select("h4").text();

            weixinAccount.setTitle(title);
            weixinAccount.setName(weixinName);

            Elements ps = textbox.select(".s-p3");
            for (Element p : ps) {
                String key = p.select(".sp-tit").text();
                String value = p.select(".sp-txt").text();
                switch (key) {
                    case "功能介绍：":
                        weixinAccount.setDescription(value);
                        break;
                    case "认证：":
                        weixinAccount.setOffical(true);
                        weixinAccount.setWeixinrenzheng(value);
                        break;
                    case "最近文章：":
                        if (!StringUtils.isEmpty(value)) {
                            weixinAccount.setLive(true);
                        } else {
                            weixinAccount.setLive(false);
                        }
                        break;
                    default:
                        logger.info("{} is what?", key);
                }

            }

            String erweima = div.select(".pos-ico img").last().attr("src");
            weixinAccount.setErweima(erweima);
            weixinAccounts.add(weixinAccount);
        }
        return weixinAccounts;
    }

    static ThreadLocal<Connection> con = new InheritableThreadLocal<Connection>() {
        @Override
        protected Connection initialValue() {
            Connection conn = null;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/weixin", "root", "123123");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return conn;
        }
    };

    //    static String createUserId() {
//        String pool = "ABCDEFGHIGKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz";
//        StringBuilder mid = new StringBuilder();
//        for (int i = 0; i < 16; i++) {
//            int rand = new Random().nextInt(pool.length());
//            mid.append(pool.charAt(rand));
//        }
//        return mid.toString();
//    }
    static String createSNUID() {
        String pool = "ABCDEFGHIGKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz";
        String demo = "60E1C16F260C930A0000000054CAE131";
        StringBuilder mid = new StringBuilder();
        for (int i = 0; i < demo.length(); i++) {
            int rand = new Random().nextInt(pool.length());
            mid.append(pool.charAt(rand));
        }
        return mid.toString();
    }
//
//    static String[] createPairUID() {
//        String middle = "4C1C920";
//        String tail = "54C50328";
//
//        String pool = "ABCDEFGHIGKLMNOPQRSTUVWXYZ0123456789";
//
//        String[] mids = new String[2];
//        for (int t = 0; t < 2; t++) {
//            StringBuilder mid = new StringBuilder();
//            for (int i = 0; i < 7; i++) {
//                int rand = new Random().nextInt(pool.length());
//                mid.append(pool.charAt(rand));
//            }
//            mids[t] = mid.toString();
//        }
//
//        StringBuilder tailbuilder = new StringBuilder();
//        for (int i = 0; i < 8; i++) {
//            int rand = new Random().nextInt(pool.length());
//            tailbuilder.append(pool.charAt(rand));
//        }
//        tail = tailbuilder.toString();
//
//
//        String[] result = new String[2];
//        for (int i = 0; i < 2; i++) {
//            String template = "4A41737B" + mids[i] + "A00000000" + tail;
//            result[i] = template;
//            System.out.println(template);
//        }
//        return result;
//    }

    public static String readCookie() {
        boolean f = true;
        do {
//            HttpHost proxy = proxyClient.fetchOne();
            goodCookie = flushCookie(null);
            if (!goodCookie.contains("SNUID")) {
                logger.info("又不给注册id了..");
                try {
                    Thread.sleep(1000 * 5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                f = false;
            }
        } while (f);
        return goodCookie;
    }


    static String flushCookie(HttpHost proxy) {
        logger.info("重新获取Cookie!!!!!");
        Set<String> newCookieSet = new HashSet<>();
        HttpClientBuilder create = HttpClientBuilder.create();
        CloseableHttpClient client = create.build();

        String url0 = "http://weixin.sogou.com/weixinwap?ie=utf8&w=&type=1&t=1422275074436&s_t=&fr=sgsearch&query=%E7%94%B5%E5%BD%B1&pg=webSearchList";
        Map<String, String> headers0 = new HashMap<>();
        headers0.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        headers0.put("Accept-Encoding", "gzip, deflate, sdch");
        headers0.put("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.6");
        headers0.put("Cache-Control", "no-cache");
        headers0.put("Connection", "keep-alive");
        headers0.put("Host", "weixin.sogou.com");
        headers0.put("Pragma", "no-cache");
        headers0.put("User-Agent", "Mozilla/5.0 (Linux; U; Android 4.0.2; en-us; Galaxy Nexus Build/ICL53F) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30");
        HttpGet weixinwap = new HttpGet(url0);
        for (Map.Entry<String, String> entry : headers0.entrySet()) {
            weixinwap.setHeader(entry.getKey(), entry.getValue());
        }
        String abtest = "";
        try {
            CloseableHttpResponse response = client.execute(weixinwap);
            Header[] headers = response.getHeaders("Set-Cookie");
            for (Header header : headers) {
                String suvCookie = header.getValue();
                abtest = suvCookie.substring(0, suvCookie.indexOf(";") + 1);
                logger.info("name : {}, value: {}", header.getName(), abtest);
                newCookieSet.add(abtest + " ");
            }
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        String url = "http://pb.sogou.com/cl.gif?uigs_productid=webapp&uigs_uuid=1422202466077081&uigs_version=v2.0&uigs_refer=http%3A%2F%2Fweixin.sogou.com%2Fweixinwap%3Fie%3Dutf8%26w%3D%26type%3D1%26t%3D1422202444223%26s_t%3D%26fr%3Dsgsearch%26query%3D%25E7%2594%25B5%25E5%25BD%25B1%26pg%3DwebSearchList&uigs_cookie=SUID%3D4A41737B2708930A0000000054C515A2&uuid=090a15a5-5d31-4c8e-9342-fc923093ef7e&query=%E7%94%B5%E5%BD%B1&weixintype=2&fr=sgsearch&type=weixin_search_wap&xy=375,667&uigs_t=1422202473903835&uigs_st=7&uigs_cl=mainBody%26href%3Djavascript%3Avoid(0)&txt=%E5%85%AC%E4%BC%97%E8%B4%A6%E5%8F%B7";
        Map<String, String> headers1 = new HashMap<>();
        headers1.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        headers1.put("Accept-Encoding", "gzip, deflate, sdch");
        headers1.put("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.6");
        headers1.put("Cache-Control", "no-cache");
        headers1.put("Connection", "keep-alive");
        headers1.put("Host", "pb.sogou.com");
        headers1.put("Pragma", "no-cache");
        headers1.put("User-Agent", "Mozilla/5.0 (Linux; U; Android 4.0.2; en-us; Galaxy Nexus Build/ICL53F) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30");


        HttpGet clgif = new HttpGet(url);
        for (Map.Entry<String, String> entry : headers1.entrySet()) {
            clgif.setHeader(entry.getKey(), entry.getValue());
        }
        String suv = "";
        try {
            CloseableHttpResponse response = client.execute(clgif);
            Header[] headers = response.getHeaders("Set-Cookie");
            for (Header header : headers) {
                String suvCookie = header.getValue();
                suv = suvCookie.substring(0, suvCookie.indexOf(";") + 1);
                logger.info("name : {}, value: {}", header.getName(), suvCookie);
                logger.info("name : {}, value: {}", header.getName(), suv);
                newCookieSet.add(suv + " ");
            }
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        newCookieSet.add("IPLOC=CN1100; ");
        newCookieSet.add("ABTEST=2|1422281335|v1;");


        String url2 = "http://weixin.sogou.com/weixinwap?ie=utf8&query=%E7%94%B5%E5%BD%B1&type=1";
        Map<String, String> headers2 = new HashMap<>();
        headers2.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        headers2.put("Accept-Encoding", "gzip, deflate, sdch");
        headers2.put("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.6");
        headers2.put("Cache-Control", "no-cache");
        headers2.put("Connection", "keep-alive");
        headers2.put("Host", "weixin.sogou.com");
        headers2.put("Pragma", "no-cache");
        headers2.put("Cookie", suv + " IPLOC=CN1100;");
        headers2.put("Referer", "http://weixin.sogou.com/wap");
        headers2.put("User-Agent", "Mozilla/5.0 (Linux; U; Android 4.0.2; en-us; Galaxy Nexus Build/ICL53F) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30");

        HttpGet wap = new HttpGet(url2);
        for (Map.Entry<String, String> entry : headers2.entrySet()) {
            wap.setHeader(entry.getKey(), entry.getValue());
        }
        try {
            CloseableHttpResponse response = client.execute(wap);
            Header[] headers = response.getHeaders("Set-Cookie");
            for (Header header : headers) {
                String suvCookie = header.getValue();
                String tmp = suvCookie.substring(0, suvCookie.indexOf(";") + 1);
                logger.info("name : {}, value: {}", header.getName(), tmp);
                newCookieSet.add(tmp + " ");

            }
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        String url3 = "http://weixin.sogou.com/antispider/?from=%2fweixinwap%3Fie%3dutf8%26w%3d%26type%3d1%26t%3d1422281320143%26s_t%3d%26fr%3dsgsearch%26query%3d%E7%94%B5%E5%BD%B1%26pg%3dwebSearchList";
        Map<String, String> headers3 = new HashMap<>();
        headers3.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        headers3.put("Accept-Encoding", "gzip, deflate, sdch");
        headers3.put("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.6");
        headers3.put("Cache-Control", "no-cache");
        headers3.put("Connection", "keep-alive");
        headers3.put("Host", "weixin.sogou.com");
//        headers2.put("Cookie", suv + " IPLOC=CN1100;");
        headers2.put("Cookie", suv + " ABTEST=2|1422281335|v1");
        headers3.put("Pragma", "no-cache");
        headers3.put("Referer", "http://weixin.sogou.com/weixinwap?ie=utf8&w=&type=2&t=1422281315519&s_t=&fr=sgsearch&query=%E7%94%B5%E5%BD%B1&pg=webSearchList");
        headers3.put("User-Agent", "Mozilla/5.0 (Linux; U; Android 4.0.2; en-us; Galaxy Nexus Build/ICL53F) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30");

        HttpGet anti = new HttpGet(url3);
        for (Map.Entry<String, String> entry : headers3.entrySet()) {
            anti.setHeader(entry.getKey(), entry.getValue());
        }
        try {
            CloseableHttpResponse response = client.execute(anti);
            Header[] headers = response.getHeaders("Set-Cookie");
            for (Header header : headers) {
                String suvCookie = header.getValue();
                String tmp = suvCookie.substring(0, suvCookie.indexOf(";") + 1);
                logger.info("name : {}, value: {}", header.getName(), tmp);
                newCookieSet.add(tmp + " ");
            }
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        StringBuilder newCookie = new StringBuilder();
        for (String tmp : newCookieSet) {
            newCookie.append(tmp);
        }
        String cookie = newCookie.toString();
        logger.info("new Cookie : {}", cookie);
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cookie;
    }


}
