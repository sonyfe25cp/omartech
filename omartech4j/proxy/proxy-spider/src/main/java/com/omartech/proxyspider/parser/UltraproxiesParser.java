package com.omartech.proxyspider.parser;

import cn.techwolf.data.gen.Proxy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class UltraproxiesParser implements  ProxyParser{


    @Override
    public List<Proxy> parse(String html) {
        Document document = Jsoup.parse(html);
        List<Proxy> proxies = new ArrayList<>();
        Elements select = document.select(".proxy");
        if(select != null && select.size() == 1){
            Element table = select.first();
            Elements trs = table.select("tr");
            if(trs != null && trs.size() > 1){
                trs.remove(0);
                for(Element tr : trs){
                    Elements tds = tr.select("td");
                    String host = tds.get(0).text();
                    String portStr = tds.get(1).text();
                    int port = Integer.parseInt(portStr);

                    Proxy proxy = new Proxy();
                    proxy.setHost(host);
                    proxy.setPort(port);
                    proxy.setProxyType("http");
                    proxies.add(proxy);
                }
            }

        }
        return proxies;
    }

    @Override
    public boolean match(String url) {
        if(url.startsWith("http://www.ultraproxies.com/")){
            return true;
        }else
        return false;
    }
}
