package com.omartech.proxyspider.parser;

import com.omartech.proxyspider.model.Proxy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * User: ChenJie
 * Date: 25/10/14
 * Time: 3:24 PM
 */
public class KuaidailiParser implements ProxyParser {
    @Override
    public List<Proxy> parse(String html) {
        List<Proxy> list = new ArrayList<>();
        Document document = Jsoup.parse(html);
        Element table = document.select("#list table").first();
        Elements trs = table.select("tr");
        for (Element tr : trs) {
            Elements tds = tr.select("td");
            if(tds.size() > 1){
                String host = tds.get(0).text();
                String portStr = tds.get(1).text();
                int port = Integer.parseInt(portStr);
                String proxyType = tds.get(3).text();
                Proxy proxy = new Proxy();
                proxy.setHost(host);
                proxy.setPort(port);
                proxy.setProxyType(proxyType);
                list.add(proxy);
            }
        }
        return list;
    }

    @Override
    public boolean match(String url) {
        if (url.startsWith("http://www.kuaidaili.com/")) {
            return true;
        } else
            return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
