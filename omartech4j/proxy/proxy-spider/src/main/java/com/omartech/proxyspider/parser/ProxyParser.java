package com.omartech.proxyspider.parser;


import cn.techwolf.data.gen.Proxy;

import java.util.List;

public interface ProxyParser {


    List<Proxy> parse(String html);

    boolean match(String url);

}
