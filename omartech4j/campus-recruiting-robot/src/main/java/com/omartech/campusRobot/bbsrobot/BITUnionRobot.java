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
    static int theta = 10000/4;
    public static void main(String[] args){
//        BITUnionRobot.publishPost("这里可以发表格类型内容么？??", "没找到如何发表格样子的东西。。。\n" +
//                "\n" +
//                "只看到有列表内容。。。?", 92);

        String page = "http://out.bitunion.org/viewthread.php?tid=10581177&fpage=1";
        findTid(page);
    }


    public static String publishPost(String title, String content, int moduleId){
        CloseableHttpClient httpclient = createLoginClient();
        String threadPage = "";
        try{


            logger.info("--------------------------------------------------------------------------");
            logger.info(content);
            logger.info("--------------------------------------------------------------------------");
            List<String> pieces = cutContentIntoPiecesNew(content);
            if(pieces.size() > 1){
                String lz = pieces.get(0);
                logger.info("*********************************************************");
                lz = lz+"共 "+ pieces.size() +" 楼，麻烦不要抢哦~~";
                logger.info(lz);
                threadPage = createPost(httpclient, title, lz, moduleId);
                int threadId = findTid(threadPage);
                for(int i = 1; i < pieces.size(); i ++){
                    try {
                        Thread.sleep(1000 * 11);
                        logger.info("休息11s，然后回帖:{}", threadPage);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    String other = pieces.get(i);
                    logger.info("*********************************************************");
                    logger.info(other);
                    replayPost(httpclient, title, other, moduleId, threadId, threadPage);
                }
            }else{
                threadPage = createPost(httpclient, title, content, moduleId);
            }
            httpclient.close();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return threadPage;
    }

    private static int findTid(String threadPage) {
        String[] splits = threadPage.substring(threadPage.indexOf("?")+1).split("&");
        int tid = 0;
        for(String split : splits){
            if(split.contains("tid=")){
                String tidstr = split.replace("tid=", "");
                tid = Integer.parseInt(tidstr);
            }
        }
        logger.info("tid : {}, from {}", tid, threadPage);
        return tid;
    }


    private static List<String> cutContentIntoPiecesNew(String content) {
        List<String> list = new ArrayList<>();
        String[] splits = content.split("\\[size=6\\]");
        StringBuilder sb = new StringBuilder();
        for(String split : splits ){
            if(split.length() == 0){
                continue;
            }
            split = "[size=6]" +split;

            int current = split.length();
            int already = sb.toString().length();
            if(current + already > theta){
                if(already > 0){
                    list.add(sb.toString());
                    sb = new StringBuilder();
                }
                if(current > theta){

                    int tt = current/theta;

                    String[] lis = split.split("\\[\\*\\]");

                    int batch = lis.length/tt;

                    for(int t = 0; t < tt ; t ++){
                        StringBuilder tm = new StringBuilder();
                        tm.append("[list=1]");
                        for(int i = t * batch; i < (t+1) * batch; i ++){
                            if(i >= lis.length){
                                break;
                            }
                            String li = lis[i];
                            li = "[*]" + li;
                            tm.append(li);
                        }
                        tm.append("[/list]");
                        list.add(tm.toString());
                    }
                }else {
                    list.add(split);
                }
            }else {
                sb.append(split);
            }
        }
        list.add(sb.toString());

        return list;
    }

    static CloseableHttpClient createLoginClient(){
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return httpclient;
    }

    static String createPost(CloseableHttpClient httpclient, String title, String content, int moduleId){
        if(content == null || content.length() < 10){
            return null;
        }

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
        map.put("tag", "469");
        map.put("subject", title);
        map.put("posticon", "0");//积分
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

    static List<NameValuePair> constructParameters(Map<String, String> map){
        List<NameValuePair> lists = new ArrayList<>();
        for(Map.Entry<String, String> entry : map.entrySet()){
            lists.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        return lists;
    }

    static String replayPost(CloseableHttpClient httpclient, String title, String content, int moduleId, int threadId, String refer){
        if(threadId == 0 || moduleId == 0){
            return null;
        }
        Map<String, String> map = new HashMap<>();
        map.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        map.put("Accept-Encoding", "gzip,deflate");
        map.put("Cache-Control", "max-age=0");
        map.put("Connection", "keep-alive");
        map.put("Content-Length", content.length()+"");//todo verify how to compute
        map.put("Content-Type", "multipart/form-data; boundary=----WebKitFormBoundary8zDNXrYEVqhoQCRe");
        map.put("Host", "out.bitunion.org");
        map.put("Origin", "http://out.bitunion.org");
        map.put("Referer", refer);
        map.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.111 Safari/537.36");
        map.put("action", "reply");
        map.put("fid", ""+moduleId);
        map.put("tid", ""+ threadId);
        map.put("replysubmit", "submit");
        map.put("fpage", "1");
        map.put("viewperm", "");
        map.put("font", "");
        map.put("tag", "0");
        map.put("subject", title);
        map.put("usesig", "1");
        map.put("message", content);
        map.put("usesig", "1");

        List<NameValuePair> nameValuePairs = constructParameters(map);
        HttpPost post = new HttpPost(
                "http://out.bitunion.org/post.php?action=reply&fid="+moduleId+"&tid="+threadId);
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
}
