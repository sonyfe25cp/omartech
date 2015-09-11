package com.omartech.proxyspider.parser;

import com.omartech.data.gen.Proxy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * User: ChenJie
 * Date: 25/10/14
 * Time: 3:04 PM
 */
public class XiciParser implements ProxyParser {
    @Override
    public List<Proxy> parse(String html) {
        Document document = Jsoup.parse(html);
        List<Proxy> list = new ArrayList<>();
        Element table = document.select("#ip_list").first();

        if (table != null) {
            table.select("tr.subtitle").remove();
            Elements trs = table.select("tr");
            for (Element tr : trs) {
                if (tr != null) {
                    Elements tds = tr.select("td");
                    if (tds != null && tds.size() > 1) {
                        String host = tds.get(1).text();
                        String portStr = tds.get(2).text();
                        int port = Integer.parseInt(portStr);
                        String proxyType = tds.get(5).text();
                        Proxy proxy = new Proxy();
                        proxy.setHost(host);
                        proxy.setPort(port);
                        proxy.setProxyType(proxyType);
                        list.add(proxy);
                    }
                }
            }
        }

        return list;
    }

    @Override
    public boolean match(String url) {
        if (url.startsWith("http://www.xici.net.co")) {
            return true;
        } else
            return false;
    }
}
