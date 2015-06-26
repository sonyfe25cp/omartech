package com.omartech.engine;

import com.omartech.data.gen.Article;
import com.omartech.data.gen.ArticleType;
import com.omartech.engine.client.ClientException;
import com.omartech.engine.client.DataClients;
import com.omartech.utils.spider.DefetcherUtils;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by OmarTech on 15-6-25.
 */
public class FetchShudong {

    private static DataClients dataClients = new DataClients("127.0.0.1:5678,127.0.0.1:5678");

    static File file = new File("shudong_urls");

    public static Set<String> loadUrls() {
        Set<String> set = new HashSet<>();
        try {
            if (file.exists()) {
                List<String> strings = FileUtils.readLines(file);
                set.addAll(strings);
                System.out.println("已有链接：" + set.size());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return set;
    }

    public static void run() {
        Set<String> alreadyCrawled = loadUrls();
        try (CloseableHttpClient client = init();) {
            for (int i = 1; i < 200; i++) {
                try {
                    Map<String, String> threadUrls = fetchThreadUrls(client, alreadyCrawled, i);
                    if (threadUrls.size() == 0) {
                        System.out.println("链接全抓完了");
                        break;
                    } else {
                        writeUrls(threadUrls);
                    }
                    fetchThread(client, threadUrls);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void writeUrls(Map<String, String> threadUrls) {
        for (Map.Entry<String, String> entry : threadUrls.entrySet()) {
            String value = entry.getValue();
            try {
                FileUtils.write(file, value + "\n", true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static CloseableHttpClient init() {
        CookieStore cookieStore = new BasicCookieStore();
        List<BasicClientCookie> list = new ArrayList<>();
        list.add(new BasicClientCookie("ngaPassportCid", "ada151b2ed10344b9f427d64bc6f04185ed7bf97"));
        list.add(new BasicClientCookie("ngaPassportUid", "227936"));
        list.add(new BasicClientCookie("ngaPassportUrlencodedUname", "%25CC%25EC%25CA%25B9%25D1%25C7%25C0%25D9"));
        list.add(new BasicClientCookie("ngacn0comInfoCheckTime", "1435239106"));
        list.add(new BasicClientCookie("ngacn0comUserInfo", "%25CC%25EC%25CA%25B9%25D1%25C7%25C0%25D9%09%25E5%25A4%25A9%25E4%25BD%25BF%25E4%25BA%259A%25E8%2595%25BE%0939%0939%09%0910%0917752%091%090%090%0928_195%2C61_165%2C34_150"));
        list.add(new BasicClientCookie("ngacn0comUserInfoCheck", "945ac4dd5f9f720daf3db8f285e289dd"));

        for (BasicClientCookie cookie : list) {
            cookie.setVersion(0);
            cookie.setDomain(".ngacn.cc");
            cookie.setPath("/");
            cookieStore.addCookie(cookie);
        }

        CloseableHttpClient client = HttpClients.custom()
                .setDefaultCookieStore(cookieStore).build();
        return client;
    }

    static Map<String, String> fetchThreadUrls(CloseableHttpClient client, Set<String> alreadyCrawled, int pageNo) throws IOException {
        String url = "http://bbs.ngacn.cc/thread.php?key=%E6%A0%91%E6%B4%9E&page=" + pageNo;
        System.out.println("开始抓取:" + pageNo + "页面");
        HttpGet get = new HttpGet(url);
        CloseableHttpResponse response = client.execute(get);
        String string = DefetcherUtils.toString(response);
        Document document = Jsoup.parse(string);
        Elements elements = document.select(".posticon");

        Map<String, String> threads = new HashMap<>();
        for (Element element : elements) {
            String area = element.select(".silver").text();
            if (!area.equals("[大漩涡]")) {
                continue;
            }
            String title = element.select(".topic").text().replaceAll("[\\[\\(].*?[\\]\\)]", "").trim();
            String href = element.select("a").first().attr("href");
            if (title.contains("帐号声望不足") || href.contains("javascript")) {
                continue;
            }
            href = "http://bbs.ngacn.cc" + href;
            System.out.println(title + " -- " + href);

            if (!alreadyCrawled.contains(href)) {
                threads.put(title, href);
            }
        }
        return threads;
    }

    static void fetchThread(HttpClient client, Map<String, String> threads) throws IOException {
        for (Map.Entry<String, String> entry : threads.entrySet()) {
            String title = entry.getKey();
            String href = entry.getValue();
            System.out.println(title + " -- " + href);
            HttpGet threadGet = new HttpGet(href);
            HttpResponse httpResponse = client.execute(threadGet);
            String threadBody = DefetcherUtils.toString(httpResponse);
            Document threadDoc = Jsoup.parse(threadBody);
            Element element = threadDoc.select("#postcontent0").first();
            String text = element.text();
            text = text.replaceAll("\\[del\\].*?\\[\\/del\\]", "");

            Element first = threadDoc.select("#postdate0").first();
            String date = first.text().split(" ")[0];
            System.out.println(date);
            Article article = new Article();
            article.setTitle(title);
            article.setContent(text);
            article.setArticleType(ArticleType.Shudong);
            article.setCreatedAt(date);

            try {
                dataClients.insertArticle(article);
            } catch (ClientException e) {
                e.printStackTrace();
            }
        }

    }


    public static void main(String[] args) {
        run();
    }

}
