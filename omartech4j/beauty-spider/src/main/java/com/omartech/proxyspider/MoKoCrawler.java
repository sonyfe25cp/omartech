package com.omartech.proxyspider;

import com.omartech.proxyspider.model.HtmlObject;
import com.omartech.proxyspider.model.MokoUser;
import com.omartech.proxyspider.service.DBService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by omar on 15-1-8.
 */
public class MoKoCrawler {
    static Logger logger = LoggerFactory.getLogger(MoKoCrawler.class);

    public static void main(String[] args) {
        MoKoCrawler mkc = new MoKoCrawler();
        try {
//            mkc.gogo();
            mkc.zhuamm();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void zhuamm() throws SQLException {//cookie会变
        headers.put("Cookie", "SERVERID=server7777; REGISTER_NUM=2; JSESSIONID=51AADCD532D1F10B58D9E746F004DB57; LAST_LOGIN_EMAIL=3120100382@bit.edu.cn; NEWMOKO_USER_LOGINKEY=65880a9a-bc5d-4960-bbbb-368dbcc6096b; Hm_lvt_8d82e75c6168ba4bc0135a08edae2a2e=1420707942,1420723839,1420731862; Hm_lpvt_8d82e75c6168ba4bc0135a08edae2a2e=1420767746; MOKO_ISUPLOADLOGO=F");
        Connection connection = con.get();
        List<MokoUser> users = DBService.fetchAll(connection);
        CloseableHttpClient client = createLoginClient();
        for (MokoUser user : users) {
            int id = user.getId();
            String url = "http://www.moko.cc/mtbModelSpace.action?userid=" + id;
            HtmlObject htmlObject = Spider.fetchPage(client, url, null, headers, "http://www.moko.cc/mtb.html");
            if (htmlObject != null) {
                String html = htmlObject.getHtml();
                if (!StringUtils.isEmpty(html)) {
                    Document parse = Jsoup.parse(html);
                    Elements elements = parse.select("#div_work_img img");
                    if (elements != null && elements.size() > 0) {
                        for (Element ele : elements) {
                            String href = ele.attr("src");
                            String source = href.replace("thumb_", "");
                            logger.info("{} : {} -> {}", new String[]{id + "", href, source});
                            DBService.insertMokoPic(id, source, href, connection);
                        }
                    }
                }
            }
        }

    }

    static Map<String, String> headers = new HashMap<>();

    static {
        headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        headers.put("Accept-Encoding", "gzip, deflate");
        headers.put("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.6");
        headers.put("Cache-Control", "no-cache");
        headers.put("Connection", "keep-alive");
        headers.put("Content-Length", "205");
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        headers.put("Cookie", "SERVERID=server7777; NEWMOKO_USER_LOGINKEY=841e56c7-e980-4ba7-ad22-0935aff08051; MOKO_ISUPLOADLOGO=F; JSESSIONID=9888FF420FD357FCBE660D852F8B66BD; Hm_lvt_8d82e75c6168ba4bc0135a08edae2a2e=1420707942,1420723839; Hm_lpvt_8d82e75c6168ba4bc0135a08edae2a2e=1420727313");
        headers.put("Host", "www.moko.cc");
        headers.put("Origin", "http://www.moko.cc");
        headers.put("Pragma", "no-cache");
        headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36");
    }

    void gogo() throws SQLException, IOException {
        String url = "http://www.moko.cc/mtbModel%7CsearchItem.action";

        Map<String, String> args = new HashMap<>();
        args.put("searchLevel", "0");
        args.put("searchPlace", "0");
        args.put("searchStyle", "0");
        args.put("searchLabel", "0");
        args.put("searchflag", "searchItem");
        args.put("searchHeight", "0");
        args.put("searchRank", "0");
        args.put("searchVerify", "0");
        args.put("moreStyle", "1");
        args.put("morePlace", "0");
        args.put("moreShoot", "0");
        args.put("moreOption", "0");
        args.put("scrollTop", "41");

        Map<String, String> style = new HashMap<>();

        style.put("1", "欧美");
        style.put("11", "性感");
        style.put("14", "甜美");
        style.put("17", "运动");
        style.put("3", "日韩");
        style.put("16", "清纯");
        style.put("10", "气质");
        style.put("12", "中国风");
        style.put("5", "复古");
        style.put("7", "学院");
        style.put("65", "型男");
        style.put("67", "男神");
        style.put("72", "潮男");
        style.put("68", "酷帅");
        style.put("69", "儒雅");
        style.put("71", "胡须男");
        style.put("73", "运动");
        style.put("70", "大叔范儿");
        style.put("66", "肌肉男");
        style.put("18", "休闲");
        style.put("15", "可爱");
        style.put("13", "淑女");
        style.put("9", "街头");
        style.put("8", "OL风");
        style.put("6", "民族");
        style.put("2", "英伦");
        style.put("74", "绅士");

        CloseableHttpClient client = createLoginClient();


        MoKoSearchListParser listParser = new MoKoSearchListParser();
        Map<Integer, MokoUser> collection = new HashMap<>();
        for (int sex = 1; sex < 3; sex++) { //sex
            for (Map.Entry<String, String> entry : style.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                args.put("searchSex", sex + "");
                args.put("searchStyle", key);

                HtmlObject htmlObject = Spider.fetchPageByPost(client, url, null, headers, args, url);
//                System.out.println(html);
                if (htmlObject != null && (!StringUtils.isEmpty(htmlObject.getHtml()))) {
                    String html = htmlObject.getHtml();
                    Map<String, String> subArgs = listParser.parseArgs(html, url);
                    int totalPage = Integer.parseInt(subArgs.get("max"));
                    subArgs.remove("max");
                    logger.info("sex :{}, style:{} has {} pages", new String[]{sex + "", value, totalPage + ""});
                    for (int i = 1; i <= totalPage; i++) {
                        logger.info("crawling sex:{}, style:{}, page {}", new String[]{sex + "", value, i + ""});
                        subArgs.put("curPage", i + "");
                        HtmlObject subPage = Spider.fetchPageByPost(client, url, null, headers, subArgs, url);
                        if (subPage != null) {
                            String subHtml = subPage.getHtml();
                            if (!StringUtils.isEmpty(subHtml)) {
                                List<MokoUser> users = listParser.parse(subHtml, url);
                                for (MokoUser user : users) {
                                    FileUtils.write(new File("moko"), user.toString() + "\n", true);
                                    MokoUser mokoUser = collection.get(user.getId());
                                    if (mokoUser == null) {
                                        user.setSex(sex);
                                        user.addTag(value);
                                        collection.put(user.getId(), user);
                                    } else {
                                        mokoUser.setSex(sex);
                                        mokoUser.addTag(value);
                                        collection.put(user.getId(), mokoUser);
                                    }
                                }
                            }
                        }
                    }
//                    args.remove("curPage");
                }
                logger.info("style:{} over", value);
            }
            logger.info("sex : {} over", sex);
        }

        try (Connection connection = con.get();) {
            for (MokoUser mokoUser : collection.values()) {
                DBService.insertMokoUser(mokoUser, connection);
            }
        }
        logger.info("crawl mokoUser : {}", collection.size());
    }

    static ThreadLocal<Connection> con = new InheritableThreadLocal<Connection>() {
        @Override
        protected Connection initialValue() {
            Connection conn = null;
            try {
                Class.forName("com.mysql.jdbc.Driver");
//                conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/beauty", "root", "");
                conn = DriverManager.getConnection("jdbc:mysql://172.16.0.35:7890/beauty", "root", "root1234");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return conn;
        }
    };


    public static CloseableHttpClient createLoginClient() {
        CloseableHttpClient httpclient = HttpClients.custom()
                .setDefaultCookieStore(new BasicCookieStore()).build();// 可以帮助记录cookie

        try {

            HttpPost loginPost = new HttpPost(
                    "http://www.moko.cc/jsps/common/login.action?tourl=");
            loginPost.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            loginPost.addHeader("Accept-Encoding", "gzip, deflate");
            loginPost.addHeader("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.6");
            loginPost.addHeader("Cache-Control", "no-cache");
            loginPost.addHeader("Connection", "keep-alive");
//            loginPost.addHeader("Content-Length", "64");
            loginPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
            loginPost.addHeader("Cookie", "SERVERID=server7777; REGISTER_NUM=2; JSESSIONID=9C3A6076455458701079AE046B8697C6; Hm_lvt_8d82e75c6168ba4bc0135a08edae2a2e=1420707942,1420723839,1420731862; Hm_lpvt_8d82e75c6168ba4bc0135a08edae2a2e=1420731879; LAST_LOGIN_EMAIL=3120100382@bit.edu.cn");
            loginPost.addHeader("Host", "www.moko.cc");
            loginPost.addHeader("Origin", "http://www.moko.cc");
            loginPost.addHeader("Pragma", "no-cache");
            loginPost.addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36");
            loginPost.addHeader("Referer", "http://www.moko.cc/jsps/common/login.jsp");


            List<NameValuePair> nvps = new ArrayList<>();
            nvps.add(new BasicNameValuePair("usermingzi", "3120100382@bit.edu.cn"));
            nvps.add(new BasicNameValuePair("userkey", "3120100"));
            nvps.add(new BasicNameValuePair("isremember", "on"));
            loginPost.setEntity(new UrlEncodedFormEntity(nvps, "GBK"));

            for (NameValuePair bnvp : nvps) {
                logger.info("nvp : {} -- {}", bnvp.getName(), bnvp.getValue());
            }
            logger.info("loginPost: {}", loginPost.toString());
            CloseableHttpResponse response2 = httpclient.execute(loginPost);
            int statusCode = response2.getStatusLine().getStatusCode();
            logger.info("status: {}", statusCode);
            switch (statusCode) {
                case 200:
                    String html = IOUtils.toString(response2.getEntity()
                            .getContent(), "GBK");
                    logger.info(html);
                    break;
                case 302:
                    Header[] allHeaders = response2.getAllHeaders();
                    for (Header header : allHeaders) {
                        logger.info("{} -- {}", header.getName(), header.getValue());
                    }
                    break;
                default:
                    break;
            }
            response2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("login over");
        return httpclient;
    }

}
