package com.omartech.engine.client;

import cn.techwolf.data.gen.Article;
import cn.techwolf.data.gen.ArticleRequest;
import cn.techwolf.data.gen.ArticleResponse;
import com.google.gson.Gson;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DataClients {
    public static final int TRY_COUNT = 3;
    private final ClientsPool pool;

    private static final Logger logger = LoggerFactory.getLogger(DataClients.class);

    // TODO ips should be zk's ips
    public DataClients(String ips) {
        logger.info("using ips: {}", ips);

        String[] parts = ips.split(",");
        if (parts.length < 2) {
            throw new IllegalArgumentException("至少2个IP，中间用,隔开，用于负载均衡");
        }
        pool = new ClientsPool(ips.split(","));

    }

    public DataClients() {
        // For development only
        this("127.0.0.1:6666,127.0.0.1:6666");
    }

    private static final String[] REQUIRED_KEYS = new String[]{"ip", "agent"};

    private final Gson gson = new Gson(); // thread safe


    // auto generated code, do not edit
    public ArticleResponse searchArticle(ArticleRequest req) throws ClientException {
        ThriftClient client = null;
        Exception e = null;

        for (int i = 0; i < TRY_COUNT; i++) {
            try {
                client = pool.getClient(client);
                ArticleResponse resp = client.client.searchArticle(req);
                pool.returnClient(client);
                return resp;

            } catch (TException e1) {
                e = e1;
                if (client != null) {
                    pool.returnBrokenClient(client);
                }
                // retry
            }
        }
        throw new ClientException(e);
    }

    // auto generated code, do not edit
    public ArticleResponse insertArticle(Article req) throws ClientException {
        ThriftClient client = null;
        Exception e = null;

        for (int i = 0; i < TRY_COUNT; i++) {
            try {
                client = pool.getClient(client);
                ArticleResponse resp = client.client.insertArticle(req);
                pool.returnClient(client);
                return resp;

            } catch (TException e1) {
                e = e1;
                if (client != null) {
                    pool.returnBrokenClient(client);
                }
                // retry
            }
        }
        throw new ClientException(e);
    }

}
