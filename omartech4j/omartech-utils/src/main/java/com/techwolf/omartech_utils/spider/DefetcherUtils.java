package com.techwolf.omartech_utils.spider;

import static java.lang.Math.min;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: feng
 * Date: 2/23/14
 * Time: 9:53 AM
 */
public class DefetcherUtils {

    public static String getResouce(String name) {
        try (InputStream is = DefetcherUtils.class.getClassLoader().getResourceAsStream(name)) {
            return IOUtils.toString(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static final String USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.107 Safari/537.36";
    private static Logger logger = LoggerFactory.getLogger(DefetcherUtils.class);

    //    private static final HttpHost[] proxies;
    private static Random rnd = new Random();

//    static {
//        String[] lines = DefetcherUtils.getResouce("http_proxies").split("\n");
//        List<HttpHost> tmp = new ArrayList<>();
//        for (String line : lines) {
//            String[] parts = line.split("\t");
//            if (parts.length > 2) {
//                String[] proxy = parts[1].split(":");
//                tmp.add(new HttpHost(proxy[0], Integer.parseInt(proxy[1])));
//            }
//        }
//        proxies = tmp.toArray(new HttpHost[tmp.size()]);
//        logger.info("use {} proxies", proxies.length);
//    }


    public static final String CHARSET = "charset=";
    public static final Charset ASCII = Charset.forName("US-ASCII");
    public static final Charset UTF_8 = Charset.forName("utf8");

    public static Charset parseCharset(String type) {
        if (type != null) {
            try {
                type = type.toLowerCase();
                int i = type.indexOf(CHARSET);
                if (i != -1) {
                    String charset = type.substring(i + CHARSET.length()).trim();
                    return Charset.forName(fixCharset(charset));
                }
            } catch (Exception ignore) {
            }
        }
        return null;
    }

    private final static String fixCharset(String s) {
        if (s.equalsIgnoreCase("gb2312")) {
            return "gbk";
        }
        return s;
    }

    private static Charset guess(String html, String patten) {
        int idx = html.indexOf(patten);
        if (idx != -1) {
            int start = idx + patten.length();
            int end = html.indexOf('"', start);
            if (end != -1) {
                try {
                    String charset = html.substring(start, end);
                    return Charset.forName(fixCharset(charset));
                } catch (Exception ignore) {
                }
            }
        }
        return null;
    }

    public static Charset detectCharset(HttpResponse resp, byte[] body) {
        Header header = resp.getFirstHeader("content-type");
        if (header != null) {
            Charset c = parseCharset(header.getValue());
            if (c != null) {
                return c;
            }
        }
        String s = new String(body, 0, min(512, body.length), ASCII);
        Charset c = guess(s, CHARSET);
        return c == null ? UTF_8 : c;
    }

    public static String toString(HttpResponse resp) throws IOException {
        byte[] bytes = EntityUtils.toByteArray(resp.getEntity());
        return new String(bytes, 0, bytes.length, detectCharset(resp, bytes));
    }


    public static Map<String, String> splitQuery(URI url) {
        Map<String, String> qp = new LinkedHashMap<String, String>();
        String query = url.getQuery();
        if (query != null && query.length() > 0) {
            String[] pairs = query.split("&");
            for (String pair : pairs) {
                int idx = pair.indexOf("=");
                try {
                    qp.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"),
                            URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
                } catch (UnsupportedEncodingException ignore) {
                }
            }
        }
        return qp;
    }

    public static String assemble(URI uri, Map<String, String> p) {
        StringBuilder sb = new StringBuilder();
        sb.append(uri.getScheme()).append("://").append(uri.getHost());
        if (uri.getPath() != null) {
            sb.append(uri.getPath());
        }

        sb.append("?");
        for (Map.Entry<String, String> param : p.entrySet()) {
            try {
                sb.append(param.getKey()).append("=").append(URLEncoder.encode(param.getValue(), "utf8"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            sb.append("&");
        }

        sb.setLength(sb.length() - 1);
        return sb.toString();
    }


    public static int rsleep(long maxtime) {
        try {
            int t = rnd.nextInt((int) maxtime);
            Thread.sleep(t);
            return t;
        } catch (InterruptedException ignore) {
        }

        return 0;
    }

    public static void sleep(long ms) {
        try {
            Thread.sleep(ms / 2);
            rsleep(ms / 2);
        } catch (InterruptedException ignore) {
        }
    }

    private static boolean isWhitespace(char c) {
        return Character.isWhitespace(c) || c == 160; // &nbsp;
    }

    public static String trimString(String s) {
        if (s != null) {
            int start = 0, end = s.length() - 1;
            while (start < s.length() && isWhitespace(s.charAt(start))) {
                start++;
            }

            while (end > 0 && isWhitespace(s.charAt(end))) {
                end--;
            }

            if (start > end) {
                return "";
            }

            return s.substring(start, end + 1);
        }

        return null;
    }

    public static String chopColon(String s) {
        if (s != null) {
            if (s.endsWith("：")) {
                s = s.substring(0, s.length() - 1);
            }
            return s;
        }
        return null;
    }

    public static String getLocation(HttpResponse resp) {
        Header location = resp.getFirstHeader("Location");
        if (location != null) {
            return location.getValue();
        } else {
            return "";
        }
    }

//    public static HttpGet httpGet(String url, String refer) {
//        return httpGet(url, refer, false);
//    }

//    public static HttpGet httpGet(String url, String refer, boolean proxy) {
//        HttpGet get = new HttpGet(url);
//        configRequest(get, refer, proxy);
//        return get;
//    }


//    public static void configRequest(HttpRequestBase base, String refer) {
//        configRequest(base, refer, false);
//    }

//    public static void configRequest(HttpRequestBase base, String refer, boolean proxy) {
//        RequestConfig.Builder config = RequestConfig.custom().setSocketTimeout(40000).
//                setConnectTimeout(15000).setRedirectsEnabled(false);
//
//        if (proxy) {
//            config.setProxy(proxies[rnd.nextInt(proxies.length)]);
//        }
//
//        base.setConfig(config.build());
//        base.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
//        base.setHeader("User-Agent", USER_AGENT);
//        base.setHeader("Accept-Language", "zh-cn,zh;q=0.5");
//        base.setHeader("Connection", "keep-alive");
//        base.setHeader("Accept-Encoding", "gzip");
//        base.setHeader("Proxy-Connection", "keep-alive");
//
//        if (refer != null && !refer.isEmpty())
//            base.setHeader("Referer", refer);
//    }


    private static final String[] IGNORE_TAGS = new String[]{"script", "style", "link", "#comment", "h2", "h1"};

//    public static String compactHtml(String html, String baseUri) {
//        if (html == null || html.isEmpty()) {
//            return "";
//        }
//        StringBuilder sb = new StringBuilder(html.length());
//        CompactHtmlVisitor vistor = new CompactHtmlVisitor(sb, baseUri);
//        PartialTraversor traversor = new PartialTraversor(vistor, IGNORE_TAGS);
//
//        Document doc = Jsoup.parse(html, baseUri);
//        List<Node> nodes = doc.body().childNodes();
//        for (Node e : nodes) {
//            traversor.traverse(e);
//        }
//        return sb.toString();
//    }

//    public static void main(String[] args) {
//        System.out.println(Long.MAX_VALUE);
//    }
}
