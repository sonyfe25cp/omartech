package com.omartech.proxyspider.parser;

import com.omartech.data.gen.Proxy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class IPAddressParser implements ProxyParser{

    @Override
    public List<Proxy> parse(String html) {
        Document doc = Jsoup.parse(html);
        Elements select = doc.select(".proxylist");
        List<Proxy> proxies = new ArrayList<>();
        if(select != null && select.size() == 1){
            Element table = select.first();
            Elements trs = table.select("tr");
            if(trs.size() > 1){
                trs.remove(0);
                for(Element tr : trs){

                    Elements tds = tr.select("td");
                    Element td = tds.get(0);
                    String text = td.text();
                    if(text.contains(":")){
                        String[] split = text.split(":");
                        String host = split[0];
                        String portStr = split[1];
                        int port = Integer.parseInt(portStr);

                        Proxy proxy = new Proxy();
                        proxy.setHost(host);
                        proxy.setPort(port);
                        proxy.setProxyType("http");
                        proxies.add(proxy);
                    }
                }
            }
        }
        return proxies;
    }

    @Override
    public boolean match(String url) {
        if(url.startsWith("http://www.ip-adress.com")){
            return true;
        }else
            return false;
    }
}
