package com.omartech.utils.spider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 对于EDU.CN类型,经过测试，无误
 * @author Chen Jie
 * @date 1 Sep, 2014
 */
public class URLRefiner {

    private static Pattern dotdot = Pattern.compile("../");

    /**
     * 找到host，完整域名，不带http
     * @param url
     * @return
     */
    public static String findHost(String url) {
        String tmp = url.replace("http://", "");
        String host = null;
        if (tmp.contains("/")) {
            int indexOf = tmp.indexOf("/");
            host = tmp.substring(0, indexOf);
        } else {
            host = tmp;
        }
        return host;
    }

    public static String findPrefix(String url) {
        String host = URLRefiner.findHost(url);
        String prefix = "http://" + host;
        if (url.endsWith("/")) {
            url = url.substring(0, url.length() - 1);
        }
        //        if(url.contains("?") || url.contains("&") || url.contains("=")){
        //        if(url.contains("?") || url.contains("&") ){
        if (url.contains("?")) {
            int location1 = url.lastIndexOf("?");
            //            int location2 = url.lastIndexOf("&");
            //            int location3 = url.lastIndexOf("=");
            //            int max = Math.max(location1, location2);
            int max = location1;
            //            max = Math.max(max, location3);
            prefix = url.substring(0, max);
        } else {
            if (url.contains("/")) {
                String left = url.replace("http://", "").replace(host, "");
                if (left.length() > 1) {
                    prefix = url.substring(0, url.lastIndexOf("/") + 1);
                }
            }
        }
        return prefix;
    }

    static Logger logger = LoggerFactory.getLogger(URLRefiner.class);

    public static String refineURL(String url, String relative) {
        String result = null;
        if (relative.startsWith("http://") || relative.startsWith("https://")) {
            return relative;
        } else {
            char charAt = relative.charAt(0);
            String host = findHost(url);
            switch (charAt) {
                case '/':
                    String tmpHost = "http://" + host;
                    result = tmpHost + relative;
                    break;
                case '.':
                    if (relative.startsWith("./")) {

                        String tmpRel = relative.replace("./", "");
                        String tmpHead = null;
                        if (url.replace("http://", "").contains("/")) {
                            tmpHead = url.substring(0, url.lastIndexOf("/"));
                        } else {
                            tmpHead = url + "/";
                        }
                        result = tmpHead + tmpRel;
                    } else if (relative.startsWith("../")) {
                        url = url.substring(0, url.lastIndexOf("/"));
                        do {
                            Matcher matcher = dotdot.matcher(relative);
                            if (matcher.find()) {
                                url = url.substring(0, url.length() - 1);
                                url = url.substring(0, url.lastIndexOf("/") + 1);
                                relative = relative.substring(3);
                            }
                        } while (relative.contains("../"));
                        result = url + relative;
                    } else {
                        logger.error("不认识这个url: {}, relative:{}", url, relative);
                    }
                    break;
                default:
                    String tmpHead2 = "http://" + host;
                    if (tmpHead2.equals(url)) {
                        result = url + "/" + relative;
                    } else if (url.equals(tmpHead2 + "/")) {
                        result = url + relative;
                    } else {
                        String left = url.replace(tmpHead2 + "/", "").replace(tmpHead2, "");
                        if (left.contains("/")) {
                            String leftHead = url.substring(0, url.lastIndexOf("/"));
                            result = leftHead + "/" + relative;
                        } else {
                            result = tmpHead2 + "/" + relative;
                        }
                    }
                    break;
            }
        }
        return result;
    }

}
