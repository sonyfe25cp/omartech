package com.omartech.proxyspider;

import com.omartech.proxyspider.model.HtmlObject;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
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

    static final boolean userProxy = false;

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


    private static HtmlObject fetchPage(String url, HttpHost proxy, Map<String, String> headers,
                                        String refer) {
        logger.info("crawling {}, with {}, from {}", new String[]{url,
                proxy == null ? " no proxy " : proxy.toHostString(), refer});
        HttpClientBuilder create = HttpClientBuilder.create();
        CloseableHttpClient client = create.build();
        HtmlObject object = null;
        HttpGet get = new HttpGet(url);
        RequestConfig.Builder config = RequestConfig.custom().setSocketTimeout(40000)
                .setConnectTimeout(15000).setRedirectsEnabled(false);
        get.setConfig(config.build());
        get.setHeader("User-Agent", USER_AGENT);
        if (headers == null || headers.size() == 0) {
            get.setHeader("Accept", "textml,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            get.setHeader("Accept-Language", "zh-cn,zh;q=0.5");
            get.setHeader("Connection", "keep-alive");
            get.setHeader("Accept-Encoding", "gzip");
            get.setHeader("Proxy-Connection", "keep-alive");
        } else {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                get.setHeader(entry.getKey(), entry.getValue());
            }
        }
        get.setHeader("Referer", refer == null ? BAIDUREFER : refer);
        try {

            HttpResponse response = client.execute(get);

            int statusCode = response.getStatusLine().getStatusCode();
            logger.debug("{} -- statusCode : {}", url, statusCode);
            switch (statusCode) {
                case 200:
                    String html = IOUtils.toString(response.getEntity().getContent());
                    object = new HtmlObject();
                    object.setHtml(html);
                    object.setUrl(url);
                    object.setRefer(refer);
                    break;
                case 301:
                    logger.info("301 -- {}", url);
                    break;
                case 302:
                    logger.info("302 -- {}", url);
                    break;
                case 400:
                    logger.info("400 -- {}", url);
                    break;
            }
            client.close();
            if (object != null) {
                String html = object.getHtml();
                logger.debug("html.size: {}", object.getHtml().length());
                logger.debug("html : {}", html);
            }
        } catch (Exception e) {
            logger.error("can't get this url: {}, with proxy: {}", url, proxy == null ? "no proxy"
                    : proxy.toHostString());
        }
        if (object == null) {
            return null;
        } else {
            return object;
        }
    }


}


