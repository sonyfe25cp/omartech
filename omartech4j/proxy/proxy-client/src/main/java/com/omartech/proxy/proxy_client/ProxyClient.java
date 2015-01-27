package com.omartech.proxy.proxy_client;

import cn.techwolf.data.gen.Proxy;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.techwolf.omartech_utils.spider.DefetcherUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by OmarTech on 15-1-24.
 */
public class ProxyClient {

    static Logger logger = LoggerFactory.getLogger(ProxyClient.class);

    private static HttpHost PROXY_OMARTECH = new HttpHost("proxy.omartech.com", 7878);
    private static HttpHost PROXY_LOCAL = new HttpHost("127.0.0.1", 7878);

    static Set<HttpHost> hostList = new HashSet<>();

    static {
        hostList.add(PROXY_LOCAL);
        hostList.add(PROXY_OMARTECH);
    }

    public ProxyClient() {
        load(currentPage);
    }

    public ProxyClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
        load(currentPage);
    }

    String ip;
    int port;
    int currentPage = 1;
    ConcurrentLinkedQueue<HttpHost> queue = new ConcurrentLinkedQueue<>();

    private List<Proxy> fetchClient(int pageNo) {
        List<Proxy> proxies = null;

        for (HttpHost host : hostList) {
            proxies = fetchClient(host.getHostName(), host.getPort(), pageNo);
            if (proxies != null && proxies.size() > 0) {
                return proxies;
            }
        }
        return proxies;

    }

    static Gson gson = new Gson();

    private List<Proxy> fetchClient(String ip, int port, int pageNo) {
        List<Proxy> proxies = new ArrayList<>();
        String url = "http://" + ip + ":" + port + "/" + pageNo;
        port = (port == 0 ? 80 : port);
        HttpClientBuilder create = HttpClientBuilder.create();
        try (CloseableHttpClient client = create.build();) {
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response = client.execute(httpGet);
            String json = DefetcherUtils.toString(response);
            if (!StringUtils.isEmpty(json)) {
                proxies = gson.fromJson(json, new TypeToken<List<Proxy>>() {
                }.getType());
            }
        } catch (HttpHostConnectException e1) {
            logger.error("HttpHostConnectException error");
        } catch (IOException e) {
            logger.error("HttpHostConnectException error");
        }
        logger.info("fetch {}, get {} proxies", url, proxies.size());
        return proxies;
    }

    public HttpHost fetchOne() {
        HttpHost poll = null;
        do {
            poll = queue.poll();
            if (poll == null) {
                currentPage++;
                int size = load(currentPage);
                if (size == 0) {
                    logger.error("no available proxy");
                    currentPage = 1;
                    break;
                }
            }
        } while (poll == null);
        return poll;
    }

    public void dropOut(HttpHost proxy) {
        if (proxy != null) {
            logger.info("{} is droped out", proxy.toHostString());
            queue.remove(proxy);
        }
    }


    private int load(int pageNo) {
        List<Proxy> proxies = new ArrayList<>();
        if (StringUtils.isEmpty(ip)) {
            proxies = fetchClient(pageNo);
        } else {
            proxies = fetchClient(ip, port, pageNo);
        }
        for (Proxy proxy : proxies) {
            queue.add(new HttpHost(proxy.getHost(), proxy.getPort()));
        }
        logger.info("proxies list size : {}", queue.size());

        return proxies.size();
    }


}
