package com.omartech.engine.service.bican;

import cn.techwolf.data.gen.Article;
import cn.techwolf.data.gen.ArticleRequest;
import cn.techwolf.data.gen.ArticleResponse;
import com.omartech.engine.service.ADataService;
import org.apache.thrift.TException;

/**
 * Created by omar on 14-12-16.
 */
public class BicanSearchService extends ADataService {


    @Override
    public ArticleResponse searchArticle(ArticleRequest req) throws TException {
        return super.searchArticle(req);
    }

    @Override
    public void insertArticle(Article article) throws TException {
        super.insertArticle(article);
    }


}
