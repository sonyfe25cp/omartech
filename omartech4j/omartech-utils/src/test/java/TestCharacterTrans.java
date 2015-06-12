import com.omartech.utils.spider.DefetcherUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.EOFException;
import java.io.IOException;
import java.net.UnknownHostException;

/**
 * Created by OmarTech on 15-6-12.
 */
public class TestCharacterTrans {

    public static void main(String[] args) {
        String url = "http://finance.sina.com.cn/chanjing/rsbd/20150608/204422377874.shtml";
        try (CloseableHttpClient client = HttpClientBuilder.create().build();) {
            String html = "";
            HttpGet get = null;
            try {
                get = new HttpGet(url);
            } catch (Exception e) {
                get.abort();
            }
            RequestConfig.Builder config = RequestConfig.custom().setSocketTimeout(40000)
                    .setConnectTimeout(15000).setRedirectsEnabled(false);
            get.setConfig(config.build());
            try {
                HttpResponse httpResponse = client.execute(get);
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                switch (statusCode) {
                    case 200:
                        html = DefetcherUtils.toString(httpResponse);
//                        System.out.println(html);
                        break;
                    default:
                        EntityUtils.consumeQuietly(httpResponse.getEntity());
                        break;
                }
            } catch (EOFException e) {
            } catch (UnknownHostException e) {
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                get.releaseConnection();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
