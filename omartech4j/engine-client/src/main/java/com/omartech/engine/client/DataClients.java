package com.omartech.engine.client;

import com.omartech.data.gen.*;
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
   public ArticleResponse insertArticle(Article article) throws ClientException {
        ThriftClient client = null;
        Exception e = null;

        for (int i = 0; i < TRY_COUNT; i++) {
            try {
                client = pool.getClient(client);
                ArticleResponse resp = client.client.insertArticle(article);
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
   public ArticleResponse increaseArticleHot(Article article) throws ClientException {
        ThriftClient client = null;
        Exception e = null;

        for (int i = 0; i < TRY_COUNT; i++) {
            try {
                client = pool.getClient(client);
                ArticleResponse resp = client.client.increaseArticleHot(article);
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
   public ArticleResponse decreaseArticleHot(Article article) throws ClientException {
        ThriftClient client = null;
        Exception e = null;

        for (int i = 0; i < TRY_COUNT; i++) {
            try {
                client = pool.getClient(client);
                ArticleResponse resp = client.client.decreaseArticleHot(article);
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
   public BeautyResponse searchBeauty(BeautyRequest req) throws ClientException {
        ThriftClient client = null;
        Exception e = null;

        for (int i = 0; i < TRY_COUNT; i++) {
            try {
                client = pool.getClient(client);
                BeautyResponse resp = client.client.searchBeauty(req);
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
   public QieyexinxiResponse searchQiyexinxi(QiyexinxiRequest req) throws ClientException {
        ThriftClient client = null;
        Exception e = null;

        for (int i = 0; i < TRY_COUNT; i++) {
            try {
                client = pool.getClient(client);
                QieyexinxiResponse resp = client.client.searchQiyexinxi(req);
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
   public JobResponse searchJobs(JobRequest req) throws ClientException {
        ThriftClient client = null;
        Exception e = null;

        for (int i = 0; i < TRY_COUNT; i++) {
            try {
                client = pool.getClient(client);
                JobResponse resp = client.client.searchJobs(req);
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
