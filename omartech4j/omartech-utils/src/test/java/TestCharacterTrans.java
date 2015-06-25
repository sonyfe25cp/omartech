import com.omartech.utils.spider.DefetcherUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.EntityUtils;

import java.io.EOFException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by OmarTech on 15-6-12.
 */
public class TestCharacterTrans {

    public static void main(String[] args) {
        String url = "http://bbs.ngacn.cc/thread.php?key=%E6%A0%91%E6%B4%9E";

        CookieStore cookieStore = new BasicCookieStore();

        List<BasicClientCookie> list = new ArrayList<>();
        list.add(new BasicClientCookie("ngaPassportCid", "ada151b2ed10344b9f427d64bc6f04185ed7bf97"));
        list.add(new BasicClientCookie("ngaPassportUid", "227936"));
        list.add(new BasicClientCookie("ngaPassportUrlencodedUname", "%25CC%25EC%25CA%25B9%25D1%25C7%25C0%25D9"));
        list.add(new BasicClientCookie("ngacn0comInfoCheckTime", "1435239106"));
        list.add(new BasicClientCookie("ngacn0comUserInfo", "%25CC%25EC%25CA%25B9%25D1%25C7%25C0%25D9%09%25E5%25A4%25A9%25E4%25BD%25BF%25E4%25BA%259A%25E8%2595%25BE%0939%0939%09%0910%0917752%091%090%090%0928_195%2C61_165%2C34_150"));
        list.add(new BasicClientCookie("ngacn0comUserInfoCheck", "945ac4dd5f9f720daf3db8f285e289dd"));

        for (BasicClientCookie cookie : list) {
            cookie.setVersion(0);
            cookie.setDomain(".ngacn.cc");
            cookie.setPath("/");
            cookieStore.addCookie(cookie);
        }

        CloseableHttpClient client = HttpClients.custom()
                .setDefaultCookieStore(cookieStore).build();


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
                    System.out.println(html);
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

    }
}
