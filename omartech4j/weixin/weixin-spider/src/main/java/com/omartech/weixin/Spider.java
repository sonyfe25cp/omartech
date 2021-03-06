package com.omartech.weixin;

import cn.techwolf.data.gen.HtmlObject;
import com.techwolf.omartech_utils.spider.DefetcherUtils;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * User: ChenJie
 * Date: 25/10/14
 * Time: 11:10 AM
 */
public class Spider {
    static Logger logger = LoggerFactory.getLogger(Spider.class);

    static ConcurrentLinkedQueue<HttpHost> queue = new ConcurrentLinkedQueue<>();

    static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.143 Safari/537.36";

    static final int MaxTimes = 3;

    public static HtmlObject fetchPage(String url, Map<String, String> headers, String refer) {
        HtmlObject object = null;
        int times = 0;
        do {
            times++;
            object = fetchPage(url, null, headers, refer);
        } while (object == null && times < MaxTimes);

        return object;
    }

    final static String BAIDUREFER = "http://www.baidu.com";


    public static HtmlObject fetchPage(String url, HttpHost proxy, Map<String, String> headers,
                                       String refer) {
        logger.info("crawling {}, with {}, from {}", new String[]{url,
                proxy == null ? " no proxy " : proxy.toHostString(), refer});
        HtmlObject object = new HtmlObject();
        HttpGet get = null;
        try {
            get = new HttpGet(url);
        } catch (Exception e) {
            get.abort();
            return object;
        }
        HttpClientBuilder create = HttpClientBuilder.create();
        CloseableHttpClient client = create.build();

        RequestConfig.Builder config = RequestConfig.custom().setSocketTimeout(15000)
                .setConnectTimeout(15000).setRedirectsEnabled(false);
        get.setConfig(config.build());
        get.setHeader("User-Agent", USER_AGENT);
        if (headers == null || headers.size() == 0) {
            get.setHeader("Accept", "textml,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            get.setHeader("Accept-Language", "zh-cn,zh;q=0.5");
            get.setHeader("Connection", "close");//origin is keep-alive
            get.setHeader("Accept-Encoding", "gzip");
            get.setHeader("Proxy-Connection", "keep-alive");
        } else {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                get.setHeader(entry.getKey(), entry.getValue());
            }
        }
        get.setHeader("Host", findHost(url));

        get.setHeader("Referer", refer == null ? BAIDUREFER : refer);

        try {
            HttpResponse response = client.execute(get);

            Header[] allHeaders = response.getAllHeaders();
            int statusCode = response.getStatusLine().getStatusCode();
            logger.debug("{} -- statusCode : {}", url, statusCode);
            if (statusCode == 200) {
                String html = DefetcherUtils.toString(response);
                object.setStatusCode(200);
                object.setHtml(html);
                object.setUrl(url);
                object.setRefer(refer);
            } else {
                logger.info("statusCode : {} -- {}", statusCode, url);
                object.setStatusCode(statusCode);
                for (Header header : allHeaders) {
                    if (header.getName().equalsIgnoreCase("Location")) {
                        logger.info("redirect to {}", header.getValue());
                        object.setRedirectUrl(header.getValue());
                    }
                }
                EntityUtils.consumeQuietly(response.getEntity());
            }
        } catch (Exception e) {
            logger.error("can't get this url: {}, with proxy: {}", url, proxy == null ? "no proxy"
                    : proxy.toHostString());
            get.abort();
        } finally {
            get.releaseConnection();
        }
        return object;
    }

    private static String findHost(String url) {
        String url2 = url.replace("http://", "");
        int index = url2.indexOf("/");
        String host = null;
        if (index <= 0) {
            host = url2;
        } else {
            host = url2.substring(0, index);
        }
        return host;
    }


}


