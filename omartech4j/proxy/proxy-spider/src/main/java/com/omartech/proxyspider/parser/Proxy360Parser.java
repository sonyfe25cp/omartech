package com.omartech.proxyspider.parser;

import cn.techwolf.data.gen.Proxy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * User: ChenJie
 * Date: 25/10/14
 * Time: 3:17 PM
 */
public class Proxy360Parser  implements ProxyParser{
    @Override
    public List<Proxy> parse(String html) {

        List<Proxy> list = new ArrayList<>();
        Document document = Jsoup.parse(html);
        Elements select = document.select("#ctl00_ContentPlaceHolder1_upProjectList");
        if(select!=null && select.size() ==1){
            Element table = select.first();
            Elements items = table.select(".proxylistitem");
            for(Element item : items){
                Elements spans = item.select("span.tbBottomLine");
                String host = spans.get(0).text();
                String portStr = spans.get(1).text();
                int port = Integer.parseInt(portStr);
                Proxy proxy = new Proxy();
                proxy.setHost(host);
                proxy.setPort(port);
                proxy.setProxyType("http");
                list.add(proxy);
            }
        }

        return list;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean match(String url) {
        if(url.startsWith("http://www.proxy360.cn/Proxy")){
            return true;
        }else
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
