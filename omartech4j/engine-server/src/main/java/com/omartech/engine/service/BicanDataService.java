package com.omartech.engine.service;

import cn.techwolf.data.gen.Article;
import cn.techwolf.data.gen.ArticleRequest;
import cn.techwolf.data.gen.ArticleResponse;
import org.apache.thrift.TException;

class BicanDataService extends ADataService{


    @Override
    public ArticleResponse searchArticle(ArticleRequest req) throws TException {
        return super.searchArticle(req);
    }

    @Override
    public void insertArticle(Article article) throws TException {
        super.insertArticle(article);
    }
}