package com.omartech.proxyspider.parser;


import com.omartech.data.gen.Proxy;

import java.util.List;

public interface ProxyParser {


    List<Proxy> parse(String html);

    boolean match(String url);

}
