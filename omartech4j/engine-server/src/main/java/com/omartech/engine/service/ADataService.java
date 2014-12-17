package com.omartech.engine.service;

import cn.techwolf.data.gen.*;
import org.apache.thrift.TException;

/**
 * Created by omar on 14-12-6.
 */
public class ADataService implements DataService.Iface {
    @Override
    public ArticleResponse searchArticle(ArticleRequest req) throws TException {
        throw new TException();
    }

    @Override
    public void insertArticle(Article article) throws TException {

    }

    @Override
    public QieyexinxiResponse searchQiyexinxi(QiyexinxiRequest req) throws TException {
        throw new TException();
    }
}
