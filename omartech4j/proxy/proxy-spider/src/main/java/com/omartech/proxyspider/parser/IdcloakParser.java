package com.omartech.proxyspider.parser;

import cn.techwolf.data.gen.Proxy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class IdcloakParser implements ProxyParser {

	private Elements tds;

	public List<Proxy> parse(String html) {


		Document document = Jsoup.parse(html);

		Elements sort = document.select("#sort");

		Element table = null;
		List<Proxy> proxies = new ArrayList<>();
		if (sort != null && sort.size() == 1) {
			table = sort.first();

			Elements trs = table.select("tr");
			trs.remove(0);// remove title

			for (Element tr : trs) {
				Proxy proxy = new Proxy();
				tds = tr.select("td");
				for (int i = 0; i < tds.size(); i++) {

					Element td = tds.get(i);
					String value = td.text();

					switch (i) {

					case 5:
						proxy.setProxyType(value);
						break;
					case 6:
						int port = Integer.parseInt(value);
						proxy.setPort(port);
						break;
					case 7:
						proxy.setHost(value);
						break;
					default:
						break;
					}

				}
				proxies.add(proxy);
			}
		}
		return proxies;
	}

    @Override
    public boolean match(String url) {
        if(url.startsWith("http://www.idcloak.com/")){
            return true;
        }else
            return false;
    }

}
