package com.omartech.proxyspider;

import com.omartech.proxyspider.model.MokoUser;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by omar on 15-1-8.
 */
public class MoKoSearchListParser {
    static Logger logger = LoggerFactory.getLogger(MoKoSearchListParser.class);

    public List<MokoUser> parse(String html, String url) {
        List<MokoUser> users = new ArrayList<>();
        String nothing = "未找到相关信息，为您推荐以下人才…您还可以直接咨询专业客服美眉哦~";
        if (!html.contains(nothing)) {
            Document document = Jsoup.parse(html);
            document.select("ul.sub_fl.fl").remove();
            Elements lis = document.select("ul.sub_fl li");
            for (Element li : lis) {
                Element first = li.select(".model_data a").first();
                String name = first.attr("title").replace("模特", "");
                String href = first.attr("href");
                String id = href.substring(href.indexOf("=") + 1);
                logger.info("id:{}, name:{}", id, name);
                MokoUser user = new MokoUser();
                user.setId(Integer.parseInt(id));
                user.setName(name);
                users.add(user);
            }
        }
        return users;

    }

    public Map<String, String> parseArgs(String html, String url) {
        Map<String, String> map = new HashMap<>();
        Document document = Jsoup.parse(html);
        Element first = document.select("#search_form").first();
        Elements inputs = first.select("input[type=hidden]");
        for (Element input : inputs) {
            String name = input.attr("name");
            String value = input.attr("value");
            if (!StringUtils.isEmpty(value)) {
                map.put(name, value);
            }
        }

        Elements elements = document.select(".page a.b.bC");
        int max = 1;
        for (Element ele : elements) {
            String text = ele.text();
            try {
                int i = Integer.parseInt(text);
                max = Math.max(i, max);
            } catch (Exception e) {
                continue;
            }
        }

        map.put("max", max + "");

        return map;
    }

}
