package com.omartech.proxyspider;

import com.google.gson.Gson;
import com.omartech.proxyspider.model.HtmlObject;
import com.omartech.proxyspider.model.WeixinAccount;
import com.omartech.proxyspider.model.WeixinPost;
import com.omartech.proxyspider.service.DBService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
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
public class Crawler {
    static Logger logger = LoggerFactory.getLogger(Crawler.class);

    public static void main(String[] args) {
        Crawler crawler = new Crawler();
        crawler.gogo();
    }

    private int cpu = Runtime.getRuntime().availableProcessors();

    private void gogo() {

        ThreadPoolExecutor executor = new ThreadPoolExecutor(cpu, cpu, 1, TimeUnit.DAYS, new ArrayBlockingQueue<Runnable>(cpu * 2), new ThreadPoolExecutor.CallerRunsPolicy());
        List<String> wordsList = new ArrayList<>();//init search words
        try {
            wordsList = FileUtils.readLines(new File("/tmp/company_names"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("wordsList.size : {}", wordsList.size());
        wordsList.add("西南录井一公司");
        int i = 0;
        int sleep = 1;
        for (String word : wordsList) {
            int maxPage = 0;
            int currentPage = 1;
            do {
                HtmlObject listPage = fetchResultsList(word, currentPage);
                if (listPage != null && listPage.getHtml() != null) {
                    String html = listPage.getHtml();
                    List<WeixinAccount> accounts = parseAccountFromHtml(html);
                    for (WeixinAccount account : accounts) {
                        new AccountDownloadWorker(account).run();
//                        executor.submit(new AccountDownloadWorker(account));
                    }
                    maxPage = parseMaxPageFromHtml(html);
                    logger.info("word: {}, maxPage : {}", word, maxPage);
                } else {
                    try {
                        logger.info("抓太快，休息{}分钟", sleep);
                        Thread.sleep(1000 * 60 * sleep);
                        sleep++;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                currentPage++;
            } while (currentPage < maxPage);
            i++;
            logger.info("{} is ok, index is :{}", word, i);
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

    private HtmlObject fetchResultsList(String word, int pageNo) {
        String refer = "http://weixin.sogou.com/";
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        headers.put("Accept-Encoding", "gzip, deflate, sdch");
        headers.put("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.6");
        headers.put("Cache-Control", "no-cache");
        headers.put("Connection", "keep-alive");
        headers.put("Cookie", "LSTMV=423%2C64; LCLKINT=36247");
        headers.put("Host", "weixin.sogou.com");
        headers.put("Pragma", "no-cache");
        headers.put("Referer", refer);
        headers.put("User-Agent", UA_COMPUTER);

        Map<String, String> args = new HashMap<>();
        args.put("type", "1");
        args.put("query", word);
        args.put("fr", "sgsearch");
        args.put("ie", "utf8");
        args.put("_ast", "1421510528");
        args.put("_asf", "null");
        args.put("w", "01029901");
        args.put("p", "40040100");
        args.put("dp", "1");
        args.put("cid", "null");

//        String url = "http://weixin.sogou.com/weixin";

        String url = "http://weixin.sogou.com/weixin?type=1&fr=sgsearch&ie=utf8&_ast=1421510528&_asf=null&w=01029901&p=40040100&dp=1&cid=null&query=" + word + "&page=" + pageNo;

        HtmlObject object = Spider.fetchPage(url, headers, refer);

        return object;
    }

    class AccountDownloadWorker implements Runnable {
        WeixinAccount account;

        public AccountDownloadWorker(WeixinAccount account) {
            this.account = account;
        }

        @Override
        public void run() {
            try {
                Connection connection = con.get();
                DBService.insertOrUpdateWeixinAccount(account, connection);
                fetchAllPosts(account, connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
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

    private List<WeixinPost> parsePostsFromXML(ResultJson resultJson) throws DocumentException {
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
                }
            }
            posts.add(post);
        }
        return posts;
    }

    private void fetchWholePost(WeixinPost weixinPost) {
        String url = weixinPost.getUrl();

        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        headers.put("Accept-Encoding", "gzip, deflate, sdch");
        headers.put("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.6");
        headers.put("Cache-Control", "no-cache");
        headers.put("Connection", "keep-alive");
        headers.put("Host", "mp.weixin.qq.com");
        headers.put("Pragma", "no-cache");
        headers.put("User-Agent", UA_WEIXIN);

        HtmlObject object = Spider.fetchPage(url, headers, null);
        if (object != null && (!StringUtils.isEmpty(object.getHtml()))) {
            String html = object.getHtml();
            Document doc = Jsoup.parse(html);
            Element first = doc.select("#js_content").first();
            String content = first.text();
            String source = first.html();
            weixinPost.setContent(content);
            weixinPost.setHtml(source);
        }
    }

    private HtmlObject fetchJsonList(WeixinAccount account, int current) {
        String url = "http://weixin.sogou.com/gzhjs?cb=sogou.weixin.gzhcb&openid=" + account.getOpenId() + "&t=1421541945093&page=" + current;
        Map<String, String> header = new HashMap<>();
        header.put("Accept", "*/*");
        header.put("Accept-Encoding", "gzip, deflate, sdch");
        header.put("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.6");
        header.put("Cache-Control", "no-cache");
        header.put("Connection", "keep-alive");
        header.put("Host", "weixin.sogou.com");
        header.put("Pragma", "no-cache");
        String refer = "";
        if (current == 1) {

        } else {
            refer = "http://weixin.sogou.com/gzh?openid=" + account.getOpenId();
        }
        header.put("User-Agent", UA_COMPUTER);

        HtmlObject object = Spider.fetchPage(url, header, refer);
        if (object != null && (!StringUtils.isEmpty(object.getHtml()))) {
            String html = object.getHtml();
//            logger.info("html:{}", html);
            String jsonStr = html.substring(html.indexOf("{"), html.lastIndexOf("}") + 1);
//            logger.info(jsonStr);
            object.setHtml(jsonStr);
        }
        return object;
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

            String erweima = div.select(".pos-ico img").first().attr("src");
            weixinAccount.setErweima(erweima);
            weixinAccounts.add(weixinAccount);
        }

//        for (WeixinAccount account : weixinAccounts) {
//            logger.info(account.toString());
//        }

        return weixinAccounts;
    }

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
                conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/weixin", "root", "");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return conn;
        }
    };

}
