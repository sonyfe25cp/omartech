package com.omartech.campusRobot.bbsrobot;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by omar on 14/10/30.
 */
public class BITUnionRobot {
    static Logger logger = LoggerFactory.getLogger(BITUnionRobot.class);

    public static void main(String[] args){
        BITUnionRobot.publishPost("这里可以发表格类型内容么？??", "没找到如何发表格样子的东西。。。\n" +
                "\n" +
                "只看到有列表内容。。。?", 92);
    }


    public static void publishPost(String title, String content, int moduleId){
        CloseableHttpClient httpclient = HttpClients.custom()
                .setDefaultCookieStore(new BasicCookieStore()).build();// 可以帮助记录cookie
        try {

            HttpPost loginPost = new HttpPost(
                    "http://out.bitunion.org/logging.php?action=login");
            List<NameValuePair> nvps = new ArrayList<>();
            nvps.add(new BasicNameValuePair("action", "login"));
            nvps.add(new BasicNameValuePair("username", "mikki"));
            nvps.add(new BasicNameValuePair("password", "nvidia7600"));
            nvps.add(new BasicNameValuePair("cookietime", "3600"));
            nvps.add(new BasicNameValuePair("referer", "/home.php?"));
            nvps.add(new BasicNameValuePair("verify", "40422"));
            nvps.add(new BasicNameValuePair("verifyimgid",
                    "20bbf91c2113e54306bb73e39642a5a0"));
            nvps.add(new BasicNameValuePair("styleid", "7"));
            nvps.add(new BasicNameValuePair("loginsubmit", "会员登录"));
            loginPost.setEntity(new UrlEncodedFormEntity(nvps, "GBK"));

            for (NameValuePair bnvp : nvps) {
                logger.info("nvp : {} -- {}", bnvp.getName(), bnvp.getValue());
            }
            logger.info("loginPost: {}", loginPost.toString());
            CloseableHttpResponse response2 = httpclient.execute(loginPost);
            logger.info("status: {}", response2.getStatusLine().getStatusCode());
            if (response2.getStatusLine().getStatusCode() == 200) {
                String html = IOUtils.toString(response2.getEntity()
                        .getContent(), "GBK");
            }
            response2.close();
            createPost(httpclient, title, content, moduleId);
            httpclient.close();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void createPost(CloseableHttpClient httpclient, String title, String content, int moduleId){
        Map<String, String> map = new HashMap<>();
        map.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        map.put("Accept-Encoding", "gzip,deflate");
        map.put("Cache-Control", "max-age=0");
        map.put("Connection", "keep-alive");
        map.put("Content-Length", content.length()+"");
        map.put("Content-Type", "multipart/form-data; boundary=----WebKitFormBoundary8zDNXrYEVqhoQCRe");
        map.put("Host", "out.bitunion.org");
        map.put("Origin", "http://out.bitunion.org");
        map.put("Referer", "http://out.bitunion.org/post.php?action=newthread&fid=92&fpage=1");
        map.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.111 Safari/537.36");
        map.put("action", "newthread");
        map.put("fid", ""+moduleId);
        map.put("topicsubmit", "submit");
        map.put("fpage", "1");
        map.put("viewperm", "");
        map.put("font", "");
        map.put("tag", "0");
        map.put("subject", title);
        map.put("posticon", "0");
        map.put("mode", "2");
        map.put("font", "Verdana");
        map.put("size", "3");
        map.put("color", "White");
        map.put("message", content);
        map.put("usesig", "1");

        List<NameValuePair> nameValuePairs = constructParameters(map);
        HttpPost post = new HttpPost(
                "http://out.bitunion.org/post.php?action=newthread&fid="+moduleId);
        try {
            post.setEntity(new UrlEncodedFormEntity(nameValuePairs, "gbk"));
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        try {
            HttpResponse res = httpclient.execute(post);
            int code = res.getStatusLine().getStatusCode();
            String html = EntityUtils.toString(res.getEntity(), "gbk");
            logger.info("html: {}", html);
            Header[] t = res.getHeaders("Location");

            logger.info("code: {}, {}", code, t[0]);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    static List<NameValuePair> constructParameters(Map<String, String> map){
        List<NameValuePair> lists = new ArrayList<>();
        for(Map.Entry<String, String> entry : map.entrySet()){
            lists.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        return lists;
    }

}
