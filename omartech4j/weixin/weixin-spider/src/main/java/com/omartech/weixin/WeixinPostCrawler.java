package com.omartech.weixin;

import cn.techwolf.data.gen.HtmlObject;
import cn.techwolf.data.gen.WeixinAccount;
import cn.techwolf.data.gen.WeixinPost;
import com.google.gson.Gson;
import com.omartech.weixin.service.DBService;
import com.omartech.weixin.service.SogouEncrypt;
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
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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
 * Created by OmarTech on 15-1-29.
 */
public class WeixinPostCrawler {

    static Logger logger = LoggerFactory.getLogger(WeixinPostCrawler.class);

    private int cpu = Runtime.getRuntime().availableProcessors();
    private static String UA_COMPUTER = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36";
    private static String UA_WEIXIN = "Mozilla/5.0 (Linux; Android 4.4.4; MI 3 Build/KTU84P) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/33.0.0.0 Mobile Safari/537.36 MicroMessenger/6.0.2.56_r958800.520 NetType/WIFI";


    public static void main(String[] args) {
        WeixinPostCrawler wpc = new WeixinPostCrawler();
        wpc.download();
//
//        WeixinAccount acc = new WeixinAccount();
//        acc.setOpenId("oIWsFtyI7gottu7fXSIwXIt2uHQw");
//        fetchJsonList(acc, 1);
    }

    String alreadyOver = "post-over-crawled";

    public void download() {
        int offset = 0;
        int batch = 1000;
        Connection connection = null;

        ThreadPoolExecutor executor = new ThreadPoolExecutor(cpu, cpu, 1, TimeUnit.DAYS,
                new ArrayBlockingQueue<Runnable>(cpu * 2),
                new ThreadPoolExecutor.CallerRunsPolicy());
        logger.info("work with {} threads", cpu);
        try {
            File postOverFile = new File(alreadyOver);
            if (postOverFile.exists()) {
                postOverFile.delete();
            }
            List<WeixinAccount> accounts = null;
            int times = 0;
            while (true) {
                boolean dbflag = true;
                do {
                    connection = con.get();
                    dbflag = DBUtils.verifyConnection(connection, "select id from weixinAccount limit 1");
                    if (!dbflag) {
                        con.remove();
                    }
                } while (!dbflag);
                accounts = DBService.findWeixinAccountsOfficalAndLive(offset, batch, connection);
                logger.info("offset:{}, batch:{}, results:{}", new String[]{offset + "", batch + "", accounts.size() + ""});
                if (accounts.size() != 0) {
                    for (WeixinAccount account : accounts) {
                        int id = account.getId();
//                        new PostDownloadWorker(account, connection).run();
                        executor.submit(new PostDownloadWorker(account));
                        FileUtils.write(postOverFile, "times: " + times + " id: " + id + "\n", true);
                    }
                    offset = offset + batch;
                } else {
                    offset = 0;
                    times++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static String key = "79cf83ea5128c3e55fd058ab5c932e52dfcbbead9cb440ebe8568f144d7ee118d044ad1c3c075553e51055b941bf0b97";

    static String reformUrl(String originUrl) {
        String sub = originUrl.replaceAll("&scene=6#wechat_redirect", "");
        String[] split = sub.split("&");
        StringBuilder sb = new StringBuilder();
        for (String tmp : split) {
            if (!tmp.startsWith("3rd")) {
                tmp = tmp.replaceAll("scene=6#rd", "scene=1");
                sb.append(tmp + "&");
            }
        }
        String over = sb.toString() + "key=" + key + "&ascene=0&uin=NjEwMjAxMzYw";
        return over;
    }


    class PostDownloadWorker implements Runnable {
        WeixinAccount account;

        public PostDownloadWorker(WeixinAccount account) {
            this.account = account;
        }
//        public PostDownloadWorker(WeixinAccount account, Connection connection) {
//            this.account = account;
//            this.connection = connection;
//        }

//        Connection connection = null;

        @Override
        public void run() {
//            fetchAllPosts(account, connection);
            Connection connection = con.get();
            fetchTodayPosts(account, connection);
        }
    }

    private void fetchAllPosts(WeixinAccount account, Connection connection) {
        int max = 0;
        int current = 1;
        boolean jump = false;
        do {
            HtmlObject jsonList = fetchJsonList(account, current);

            if (jsonList != null && (!StringUtils.isEmpty(jsonList.getHtml()))) {
                String html = jsonList.getHtml();
                String jsons = html;//filter json from html

                ResultJson resultJson = gson.fromJson(jsons, ResultJson.class);
                max = resultJson.totalPages;
//                logger.info("max : {}", max);
                try {
                    List<WeixinPost> weixinPosts = parsePostsFromXML(resultJson);
                    int already = 0;
                    for (WeixinPost weixinPost : weixinPosts) {
                        weixinPost.setOpenId(account.getOpenId());
                        fetchWholePost(weixinPost);
                        boolean flag = DBService.insertOrUpdateWeixinPost(weixinPost, connection);
                        if (flag == false) {//jump logic
                            already++;
                        }
                    }
                    if (already == weixinPosts.size()) {
                        jump = true;
                    }
                } catch (DocumentException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            current++;
        } while (current < max && (!jump));
    }

    private final static void fetchTodayPosts(WeixinAccount account, Connection connection) {
        int current = 1;
        HtmlObject jsonList = fetchJsonList(account, current);

        if (jsonList != null && (!StringUtils.isEmpty(jsonList.getHtml()))) {
            String html = jsonList.getHtml();
            String jsons = html;//filter json from html

            try {
                ResultJson resultJson = gson.fromJson(jsons, ResultJson.class);
                List<WeixinPost> weixinPosts = parsePostsFromXML(resultJson);
                for (WeixinPost weixinPost : weixinPosts) {
                    boolean postExist = DBService.isPostExist(weixinPost, connection);
                    if (!postExist) {
                        weixinPost.setOpenId(account.getOpenId());
                        fetchWholePost(weixinPost);
                        weixinPost.setCreatedAt(DateFormatUtils.format(new Date(), "yyyy-MM-dd"));
                        DBService.insertWeixinPost(weixinPost, connection);
                    }
                }
            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static List<WeixinPost> parsePostsFromXML(ResultJson resultJson) throws DocumentException {
        List<String> items = resultJson.items;
        List<WeixinPost> posts = new ArrayList<>(items.size());
        for (String item : items) {
            WeixinPost post = new WeixinPost();
            item = item.replaceAll("<\\/", "</");
            org.dom4j.Document document = DocumentHelper.parseText(item);
            org.dom4j.Element rootElement = document.getRootElement();
            Iterator itemPare = rootElement.elementIterator("item");
            while (itemPare.hasNext()) {
                org.dom4j.Element firstLoop = (org.dom4j.Element) itemPare.next();
                Iterator displays = firstLoop.elementIterator("display");
                while (displays.hasNext()) {
                    org.dom4j.Element displayNext = (org.dom4j.Element) displays.next();
                    String title = displayNext.elementTextTrim("title");
                    String url = displayNext.elementTextTrim("url");
                    String imglink = displayNext.elementTextTrim("imglink");
                    String headimage = displayNext.elementTextTrim("headimage");
                    String content168 = displayNext.elementTextTrim("content168");
                    String openid = displayNext.elementTextTrim("openid");
                    String date = displayNext.elementTextTrim("date");
                    post.setTitle(title);
                    post.setUrl(url);
                    post.setImgLink(imglink);
                    if (StringUtils.isEmpty(headimage)) {
                        headimage = "";
                    }
                    post.setHeadImg(headimage);
                    post.setContent168(content168);
                    post.setDate7(date);
                    post.setOpenId(openid);
                }
            }
            posts.add(post);
        }
        return posts;
    }

    static String CONNECTIONSTATUS = "close";

    private static void fetchWholePost(WeixinPost weixinPost) {
        String url = weixinPost.getUrl();
        url = reformUrl(url);

        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        headers.put("Accept-Encoding", "gzip, deflate, sdch");
        headers.put("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.6");
        headers.put("Cache-Control", "no-cache");
        headers.put("Connection", CONNECTIONSTATUS);
        headers.put("Host", "mp.weixin.qq.com");
        headers.put("Pragma", "no-cache");
        headers.put("User-Agent", UA_WEIXIN);

        HtmlObject object = Spider.fetchPage(url, headers, null);
        if (object != null && (!StringUtils.isEmpty(object.getHtml()))) {
            String html = object.getHtml();
            Document doc = Jsoup.parse(html);
            Element first = doc.select("#js_content").first();
            if (first != null) {
                String content = first.text();
                String source = first.html();
                weixinPost.setContent(content);
                weixinPost.setHtml(source);
                try {
                    String rn = doc.select("#readNum").text();
                    if (!StringUtils.isEmpty(rn)) {
                        weixinPost.setReadCount(Integer.parseInt(rn));
                    }
                    String ln = doc.select("#likeNum").text();
                    if (!StringUtils.isEmpty(ln)) {
                        weixinPost.setVoteCount(Integer.parseInt(ln));
                    }
                } catch (Exception e) {
                    logger.info("数字解析错误:{}", e.getMessage());
                }
            }
        }
    }

    static String goodCookie = readCookie();

    public static String readCookie() {
        boolean f = true;
        do {
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

        String url0 = "http://weixin.sogou.com/gzh?openid=oIWsFtyI7gottu7fXSIwXIt2uHQw";
        Map<String, String> headers0 = new HashMap<>();
        headers0.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        headers0.put("Accept-Encoding", "gzip, deflate, sdch");
        headers0.put("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.6");
        headers0.put("Cache-Control", "no-cache");
        headers0.put("Connection", CONNECTIONSTATUS);
        headers0.put("Cookie", "SUV=00AA640672F73890559CF2F155C3A321; LSTMV=971%2C288; LCLKINT=291634");
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
            logger.info("***************cookies in the first req***************");
            for (Header header : headers) {
                String suvCookie = header.getValue();
                abtest = suvCookie.substring(0, suvCookie.indexOf(";") + 1);
                logger.info("name : {}, value: {}", header.getName(), abtest);
                newCookieSet.add(abtest + " ");
            }
            logger.info("***************cookies in the first req over***************");

            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        String url = "http://pb.sogou.com/pv.gif?uigs_productid=weixin&type=encrypt&stype=1&wxtest=1&openid=oIWsFtyI7gottu7fXSIwXIt2uHQw&hdq=sogou&wxsuv=00AA640672F73890559CF2F155C3A321&eqs=XusfoDygsdi3occZAaP0puC2Py3Xfl6bmFeQQKfiiZiA7NkFoBGAkqCidB%2BJLWcEhNO%2F9&ekv=7";
        Map<String, String> headers1 = new HashMap<>();
        headers1.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        headers1.put("Accept-Encoding", "gzip, deflate, sdch");
        headers1.put("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.6");
        headers1.put("Cache-Control", "no-cache");
        headers1.put("Connection", CONNECTIONSTATUS);
        headers1.put("Host", "pb.sogou.com");
        headers1.put("Pragma", "no-cache");
        headers1.put("Referer", url0);
        headers1.put("User-Agent", "Mozilla/5.0 (Linux; U; Android 4.0.2; en-us; Galaxy Nexus Build/ICL53F) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30");

        HttpGet clgif = new HttpGet(url);
        for (Map.Entry<String, String> entry : headers1.entrySet()) {
            clgif.setHeader(entry.getKey(), entry.getValue());
        }
        String suv = "";
        try {
            CloseableHttpResponse response = client.execute(clgif);
            Header[] headers = response.getHeaders("Set-Cookie");
            logger.info("***************cookies in the second req***************");
            for (Header header : headers) {
                String suvCookie = header.getValue();
                suv = suvCookie.substring(0, suvCookie.indexOf(";") + 1);
                logger.info("name : {}, value: {}", header.getName(), suv);
                newCookieSet.add(suv + " ");
            }
            logger.info("***************cookies in the second req over***************");
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
        headers2.put("Connection", CONNECTIONSTATUS);
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
            logger.info("***************cookies in the third req***************");
            for (Header header : headers) {
                String suvCookie = header.getValue();
                String tmp = suvCookie.substring(0, suvCookie.indexOf(";") + 1);
                logger.info("name : {}, value: {}", header.getName(), tmp);
                newCookieSet.add(tmp + " ");
            }
            logger.info("***************cookies in the third req over***************");
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
        headers3.put("Connection", CONNECTIONSTATUS);
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
            logger.info("***************cookies in the fourth req over***************");
            for (Header header : headers) {
                String suvCookie = header.getValue();
                String tmp = suvCookie.substring(0, suvCookie.indexOf(";") + 1);
                logger.info("name : {}, value: {}", header.getName(), tmp);
                newCookieSet.add(tmp + " ");
            }
            logger.info("***************cookies in the fourth req over***************");
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


    private final static HtmlObject fetchJsonList(WeixinAccount account, int current) {

//        String url = "http://weixin.sogou.com/gzhjs?cb=sogou.weixin.gzhcb&openid=" + account.getOpenId() + "&t=1421541945093&page=" + current;

        String s = SogouEncrypt.generatePostAjaxUrl(account.getOpenId());
        String url = s + "&page=" + current;

        boolean flag = false;
        HtmlObject object = null;
        int times = 0;
        do {
            Map<String, String> header = new HashMap<>();
            header.put("Accept", "*/*");
            header.put("Accept-Encoding", "gzip, deflate, sdch");
            header.put("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.6");
            header.put("Cache-Control", "no-cache");
            header.put("Connection", CONNECTIONSTATUS);
            header.put("Host", "weixin.sogou.com");
            header.put("Pragma", "no-cache");
            header.put("Cookie", goodCookie);
            String refer = "";
            if (current == 1) {
                refer = "http://weixin.sogou.com";
            } else {
                refer = "http://weixin.sogou.com/gzh?openid=" + account.getOpenId();
            }
            header.put("User-Agent", UA_COMPUTER);

            object = Spider.fetchPage(url, header, refer);
            if (object != null && (!StringUtils.isEmpty(object.getHtml()))) {
                String html = object.getHtml();
                int ind = html.indexOf("{");
                int lastIndexOf = html.lastIndexOf("}");
                if (ind != -1 && lastIndexOf != -1) {
                    String jsonStr = html.substring(ind, lastIndexOf + 1);
                    object.setHtml(jsonStr);
                }
            }
            switch (object.getStatusCode()) {
                case 200:
                    flag = true;
                    break;
                default:
                    object = null;
                    goodCookie = WeixinAccountCrawler.readCookie();
                    break;
            }
            times++;
            if (times > 5) {
                logger.error("这url有问题：{}, cookie:{}", url, goodCookie);
                break;
            }
        } while (!flag);
        System.out.println(object);
        return object;
    }

    static Gson gson = new Gson();

    class ResultJson {
        public int totalItems;
        public int totalPages;
        public int page;
        public List<String> items;
    }

    static ThreadLocal<Connection> con = new InheritableThreadLocal<Connection>() {
        @Override
        protected Connection initialValue() {
            Connection conn = null;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://10.1.0.171:3306/weixin", "root", "123123");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return conn;
        }
    };
}
