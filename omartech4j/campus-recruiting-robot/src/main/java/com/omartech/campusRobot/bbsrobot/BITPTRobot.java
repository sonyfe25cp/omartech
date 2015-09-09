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
 * Created by OmarTech on 15-7-10.
 */
public class BITPTRobot {

    static Logger logger = LoggerFactory.getLogger(BITPTRobot.class);

    public static void main(String[] args) {
        CloseableHttpClient loginClient = createLoginClient();
//        createPost(loginClient, "有没有一起搞PTR的？", "S4开始了~~一起搞的留个ID呗~~", 83);
        replayPost(loginClient, "", "确实如此",50, 405087, "http://out.bitpt.cn/bbs/forum.php?mod=viewthread&tid=405087&extra=page%3D1");
    }

    public static CloseableHttpClient createLoginClient() {
        CloseableHttpClient httpclient = HttpClients.custom()
                .setDefaultCookieStore(new BasicCookieStore()).build();// 可以帮助记录cookie
        try {
            HttpPost loginPost = new HttpPost(
                    "http://out.bitpt.cn/bbs/member.php?mod=logging&action=login&loginsubmit=yes&infloat=yes&lssubmit=yes&inajax=1");
            List<NameValuePair> nvps = new ArrayList<>();
            nvps.add(new BasicNameValuePair("fastloginfield", "username"));
            nvps.add(new BasicNameValuePair("username", "mikki_hunter"));
            nvps.add(new BasicNameValuePair("password", "omarin1304"));
            nvps.add(new BasicNameValuePair("quickforward", "yes"));
            nvps.add(new BasicNameValuePair("handlekey", "ls"));
            loginPost.setEntity(new UrlEncodedFormEntity(nvps, "GBK"));

            for (NameValuePair bnvp : nvps) {
                logger.info("nvp : {} -- {}", bnvp.getName(), bnvp.getValue());
            }
            logger.info("loginPost: {}", loginPost.toString());
            CloseableHttpResponse response2 = httpclient.execute(loginPost);
            logger.info("status: {}", response2.getStatusLine().getStatusCode());
            if (response2.getStatusLine().getStatusCode() == 200) {
                String html = IOUtils.toString(response2.getEntity().getContent(), "GBK");
                logger.info("html:{}", html);
            }
            Header[] allHeaders = response2.getAllHeaders();
            for (Header header : allHeaders) {
                System.out.println(header);
            }
            response2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return httpclient;
    }

    static String replayPost(CloseableHttpClient httpclient, String title, String content, int moduleId, int threadId, String refer) {
        if (threadId == 0 || moduleId == 0 || content.length() == 0) {
            return null;
        }
        Map<String, String> map = new HashMap<>();
        map.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        map.put("Accept-Encoding", "gzip,deflate");
        map.put("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.6");
        map.put("Cache-Control", "no-cache");
        map.put("Connection", "close");
        map.put("Content-Length", content.length() + "");//todo verify how to compute
        map.put("Content-Type", "application/x-www-form-urlencoded");
        map.put("Host", "out.bitpt.cn");
        map.put("Origin", "http://out.bitpt.cn");
        map.put("Referer", refer);
        map.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.111 Safari/537.36");
        map.put("formhash", "0209c5fc");
//        map.put("posttime", "1436497468");
        map.put("wysiwyg", "1");
        map.put("typeid", "0");
        map.put("noticeauthor", "");
        map.put("noticetrimstr", "");
        map.put("noticeauthormsg", "");
        map.put("subject", title);
        map.put("message", content);
        map.put("save", "");
        map.put("uploadalbum", "5067");
        map.put("newalbum", "请输入相册名称");
        map.put("usesig", "1");
        List<NameValuePair> nameValuePairs = constructParameters(map);
        HttpPost post = new HttpPost(
                "http://out.bitpt.cn/bbs/forum.php?mod=post&action=reply&fid="+moduleId+"&tid="+threadId+"&extra=&replysubmit=yes");
        try {
            post.setEntity(new UrlEncodedFormEntity(nameValuePairs, "gbk"));
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        String threadPage = "http://out.bitpt.cn";
        try {
            HttpResponse res = httpclient.execute(post);
            int code = res.getStatusLine().getStatusCode();
            String html = EntityUtils.toString(res.getEntity(), "gbk");
            logger.info("html: {}", html);
            Header[] t = res.getHeaders("Location");
            threadPage += t[0].getValue();
            logger.info("code: {}, {}", code, t[0]);
            logger.info("threadpage：{}", threadPage);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return threadPage;
    }

    static String createPost(CloseableHttpClient httpclient, String title, String content, int moduleId) {
        if (content == null || content.length() < 10) {
            return null;
        }

        Map<String, String> map = new HashMap<>();
        map.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        map.put("Accept-Encoding", "gzip,deflate");
        map.put("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.6");
        map.put("Cache-Control", "no-cache");
        map.put("Connection", "close");
        map.put("Content-Length", content.length() + "");
        map.put("Content-Type", "application/x-www-form-urlencoded");
        map.put("Host", "out.bitpt.cn");
        map.put("Origin", "http://out.bitpt.cn");
        map.put("Referer", "http://bitpt.cn/bbs/forum.php?mod=post&action=newthread&fid=83");
        map.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.111 Safari/537.36");
        map.put("formhash", "0209c5fc");
        map.put("posttime", "1436495928");
        map.put("wysiwyg", "1");
        map.put("typeid", "0");
        map.put("subject", title);
        map.put("message", content);
        map.put("tags", "");
        map.put("save", "");
        map.put("uploadalbum", "5067");
        map.put("newalbum", "请输入相册名称");
        map.put("usesig", "1");
        map.put("allownoticeauthor", "1");

        List<NameValuePair> nameValuePairs = constructParameters(map);
        HttpPost post = new HttpPost(
                "http://out.bitpt.cn/bbs/forum.php?mod=post&action=newthread&extra=&topicsubmit=yes&fid=" + moduleId);
        try {
            post.setEntity(new UrlEncodedFormEntity(nameValuePairs, "gbk"));
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        String threadPage = "http://out.bitunion.org/";
        try {
            HttpResponse res = httpclient.execute(post);
            int code = res.getStatusLine().getStatusCode();
            String html = EntityUtils.toString(res.getEntity(), "gbk");
            logger.info("html: {}", html);
            Header[] t = res.getHeaders("Location");
            threadPage += t[0].getValue();
            logger.info("code: {}, {}", code, t[0]);
            logger.info("threadpage：{}", threadPage);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return threadPage;
    }

    static List<NameValuePair> constructParameters(Map<String, String> map) {
        List<NameValuePair> lists = new ArrayList<>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            lists.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        return lists;
    }
}
