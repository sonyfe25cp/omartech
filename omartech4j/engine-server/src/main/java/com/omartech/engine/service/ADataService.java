package com.omartech.engine.service;

import cn.techwolf.data.gen.*;
import org.apache.thrift.TException;

import java.io.IOException;

/**
 * Created by omar on 14-12-6.
 */
public class ADataService extends AIndexSearcher implements DataService.Iface {

    public ADataService(String indexPath) throws IOException {
        super(indexPath);
    }

    @Override
    public ArticleResponse searchArticle(ArticleRequest req) throws TException {
        throw new TException();
    }

    @Override
    public ArticleResponse insertArticle(Article article) throws TException {
        throw new TException();
    }

    @Override
    public BeautyResponse searchBeauty(BeautyRequest req) throws TException {
        throw new TException();
    }

    @Override
    public QieyexinxiResponse searchQiyexinxi(QiyexinxiRequest req) throws TException {
        throw new TException();
    }

    @Override
    public JobResponse searchJobs(JobRequest req) throws TException {
        throw new TException();
    }

}
