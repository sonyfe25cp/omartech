package com.omartech.proxyspider.parser;

import com.omartech.proxyspider.model.Proxy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by omar on 15-1-8.
 */
public class ProxyRuParser implements ProxyParser {
    static Logger logger = LoggerFactory.getLogger(ProxyRuParser.class);

    @Override
    public List<Proxy> parse(String html) {
        List<Proxy> list = new ArrayList<>();
        Document document = Jsoup.parse(html);
        Elements rawTds = document.select("td[bgcolor] b");
        Element tbody = null;
        if (rawTds != null && rawTds.size() > 0) {
            for (Element element : rawTds) {
                String text = element.text();
                if (text.equals("地理位置")) {
                    tbody = element.parent().parent().parent();
                    break;
                }
            }
        }
        if (tbody != null) {
            Elements trs = tbody.select("tr");
            trs.remove(0);
            for (Element element : trs) {
                Elements tds = element.select("td");
                String ip = tds.get(1).text();
                String portEle = tds.get(2).text();
                String typeEle = tds.get(3).text();
                String location = tds.get(4).text();

                Proxy proxy = new Proxy();
                proxy.setHost(ip);
                proxy.setComment(typeEle);
                proxy.setPort(Integer.parseInt(portEle));
                proxy.setProxyType("http");
                proxy.setLocation(location);
                list.add(proxy);
            }
        }
        return list;
    }

    @Override
    public boolean match(String url) {
        if (url.startsWith("http://www.proxy.com.ru/")) {
            return true;
        } else
            return false;

    }
}
