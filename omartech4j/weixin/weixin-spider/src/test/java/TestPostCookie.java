import cn.techwolf.data.gen.HtmlObject;
import com.omartech.weixin.Spider;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by OmarTech on 15-7-8.
 */
public class TestPostCookie {
    public static void main(String[] args) {
        Map<String, String> header = new HashMap<>();
        header.put("Accept", "*/*");
        header.put("Accept-Encoding", "gzip, deflate, sdch");
        header.put("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.6");
        header.put("Cache-Control", "no-cache");
        header.put("Connection", "close");
        header.put("Host", "weixin.sogou.com");
        header.put("Pragma", "no-cache");
        header.put("Cookie", "SNUID=C449C9C6FCFEE1A1CFD19112FCB2C8AF;\n" +
                "ABTEST=3|1436349591|v1; \n" +
                "IPLOC=CN1100;\n" +
                "SUV=0043100672F73890559CF497B91B5513; \n" +
                "ABTEST=2|1422281335|v1;\n" +
                "SUID=38B5323D6B20900A00000000559CF497; \n" +
                "LSTMV=409%2C115; \n" +
                "LCLKINT=56539");
        String refer = "";
        header.put("User-Agent", "Mozilla/5.0 (Linux; U; Android 4.0.2; en-us; Galaxy Nexus Build/ICL53F) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30");
        String url = "http://weixin.sogou.com/gzhjs?cb=sogou.weixin.gzhcb&openid=oIWsFt6STtXZJuZJT5wrBjMwUuXU&eqs=QysaobFgVSFmoBQpdBpELueZlDiRlF2ckCAAk6Be6Zwf8GKFo48pKUYfgFOULuXgwlVhT&ekv=7&page=2";
        HtmlObject htmlObject = Spider.fetchPage(url, header, refer);
        System.out.println(htmlObject.getHtml());

    }
}
