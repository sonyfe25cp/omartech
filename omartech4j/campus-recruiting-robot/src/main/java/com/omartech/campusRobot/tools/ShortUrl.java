package com.omartech.campusRobot.tools;

import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;

/**
 * Created by omar on 14/11/2.
 */
public class ShortUrl {
    static Logger logger = LoggerFactory.getLogger(ShortUrl.class);

    public static String shortIt(String longUrl) {
        if(!longUrl.startsWith("http")){
            longUrl = "http://" + longUrl;
        }
        CloseableHttpClient httpclient = HttpClients.custom().build();// 可以帮助记录cookie
        String result = longUrl;
        String uri = "http://vwz.me/API.php?url=" + longUrl + "&callback=json";
        HttpGet get = new HttpGet(uri);
        try {
            CloseableHttpResponse execute = httpclient.execute(get);
            int statusCode = execute.getStatusLine().getStatusCode();
            switch (statusCode) {
                case 200:
                    String html = IOUtils.toString(execute.getEntity()
                            .getContent(), "UTF-8");
                    String json = html.substring(5, html.length() - 2);
                    logger.info("json : {}", json);
                    result = parseMsg(json);
                    break;
                default:
                    break;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            httpclient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("short {} to {}", longUrl, result);
        return result;
    }

    class ShortUrlVWZ {
        String status;
        String msg;

    }

    public static String parseMsg(String json) {
        Gson gson = new Gson();
        ShortUrlVWZ shortUrlVWZ = gson.fromJson(json, ShortUrlVWZ.class);
        return shortUrlVWZ.msg;
    }


    public static void main(String[] args) {
        String longUrl = "www.laibican.com";
        String s = ShortUrl.shortIt(longUrl);
        logger.info(s);
    }

}
