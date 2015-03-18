package com.omartech.weixin;

import cn.techwolf.data.gen.HtmlObject;
import cn.techwolf.data.gen.WeixinAccount;
import cn.techwolf.data.gen.WeixinPost;
import com.google.gson.Gson;
import com.omartech.weixin.service.DBService;
import com.techwolf.omartech_utils.DBUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
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
    }

    String alreadyOver = "/tmp/postOver";

    public void download() {
        int offset = 0;
        int batch = 1000;
        Connection connection = null;

//        ThreadPoolExecutor executor = new ThreadPoolExecutor(cpu, cpu, 1, TimeUnit.DAYS,
//                new ArrayBlockingQueue<Runnable>(cpu * 2),
//                new ThreadPoolExecutor.CallerRunsPolicy());
        logger.info("work with {} threads", cpu);
        try {
            File postOverFile = new File(alreadyOver);
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
                accounts = DBService.findWeixinAccounts(offset, batch, connection);
                logger.info("offset:{}, batch:{}, results:{}", new String[]{offset + "", batch + "", accounts.size() + ""});
                if (accounts.size() != 0) {
                    for (WeixinAccount account : accounts) {
                        int id = account.getId();
                        new PostDownloadWorker(account).run();
//                        executor.submit(new PostDownloadWorker(account));
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

        @Override
        public void run() {
            Connection connection = null;
            boolean dbflag = true;
            do {
                connection = con.get();
                dbflag = DBUtils.verifyConnection(connection, "select id from weixinAccount limit 1");
                if (!dbflag) {
                    con.remove();
                }
            } while (!dbflag);
//            fetchAllPosts(account, connection);
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

    private static void fetchTodayPosts(WeixinAccount account, Connection connection) {
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

    static String goodCookie = WeixinAccountCrawler.readCookie();

    private static HtmlObject fetchJsonList(WeixinAccount account, int current) {

        String url = "http://weixin.sogou.com/gzhjs?cb=sogou.weixin.gzhcb&openid=" + account.getOpenId() + "&t=1421541945093&page=" + current;
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
                conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/weixin", "root", "123123");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return conn;
        }
    };
}
