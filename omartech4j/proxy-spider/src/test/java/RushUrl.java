import com.omartech.proxyspider.model.HtmlObject;
import com.omartech.proxyspider.model.Proxy;
import com.omartech.proxyspider.service.DBService;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by omar on 14/11/5.
 */
public class RushUrl {
    static Logger logger = LoggerFactory.getLogger(RushUrl.class);

    public static void main(String[] args) {
        String url = "http://jiaoyu.baidu.com/BeautyBws/vote?beautyId=420&collegeId=97";
        RushUrl ru = new RushUrl();
        try {
            ru.test(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.143 Safari/537.36";
    final static String BAIDUREFER = "http://www.baidu.com";


    void test(String url) throws SQLException {
        Connection connection = con.get();
        List<Proxy> https = DBService.findProxyList(100, "http", connection);
        for (Proxy http : https) {
            HttpHost proxy = new HttpHost(http.getHost(), http.getPort());
            rush(url, null, "http://jiaoyu.baidu.com/beauty/index", proxy);
        }
    }

    static ThreadLocal<Connection> con = new InheritableThreadLocal<Connection>() {
        @Override
        protected Connection initialValue() {
            Connection conn = null;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/proxy", "root", "");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return conn;
        }
    };


    public static void rush(String url, Map<String, String> headers, String refer, HttpHost proxy) {
        logger.info("rush {} , with {}", url, proxy.toHostString());
        HttpClientBuilder create = HttpClientBuilder.create();
        CloseableHttpClient client = create.build();
        HtmlObject object = null;
        HttpGet get = new HttpGet(url);
        RequestConfig.Builder config = RequestConfig.custom().setSocketTimeout(4000)
                .setConnectTimeout(3000).setRedirectsEnabled(false);
        if (proxy != null) {
            config.setProxy(proxy);
        }
        get.setConfig(config.build());
        get.setHeader("User-Agent", USER_AGENT);
        if (headers == null || headers.size() == 0) {
            get.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
            get.setHeader("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.6");
            get.setHeader("Connection", "keep-alive");
            get.setHeader("Accept-Encoding", "gzip,deflate,sdch");
            get.setHeader("Proxy-Connection", "keep-alive");
            get.setHeader("Host", "jiaoyu.baidu.com");
            get.setHeader("X-Requested-With", "XMLHttpRequest");
        } else {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                get.setHeader(entry.getKey(), entry.getValue());
            }
        }
        get.setHeader("Referer", refer == null ? BAIDUREFER : refer);
        try {

            HttpResponse response = client.execute(get);

            client.close();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}