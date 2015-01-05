package com.omartech.engine.service;

import cn.techwolf.data.gen.Article;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.NIOFSDirectory;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by omar on 14-12-24.
 */
public class AIndexBuilder extends AIndexService {
    static Logger logger = LoggerFactory.getLogger(AIndexBuilder.class);

    public AIndexBuilder(String indexPath) throws IOException {
        this.indexPath = indexPath;
        init();
    }

    void init() throws IOException {
        File folder = new File(indexPath);
        if (folder.exists()) {
            folder.delete();
        }
        Directory dir = NIOFSDirectory.open(folder);
        IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_48, analyzer);
        writer = new IndexWriter(dir, conf);
    }

    protected IndexWriter writer;

    public void build(String sql, Connection connection) throws InterruptedException, SQLException, IOException {
        logger.info("begin to index on {}", indexPath);
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(cpu, cpu, 1, TimeUnit.DAYS, new ArrayBlockingQueue<Runnable>(cpu * 2), new ThreadPoolExecutor.CallerRunsPolicy());
        List<Article> articles = null;
        long min = 0;
        int batch = 100;
        int count = 0;
        do {
            articles = fetchList(min, batch, sql, connection);
            for (Article article : articles) {
//                poolExecutor.submit(new Worker(article));
                new Worker(article).run();
                long id = article.getId();
                min = Math.max(id, min);
                count++;
                if (count % 1000 == 0) {
                    logger.info("process {}", count);
                }
            }
        } while (articles.size() > 0);
        logger.info("{} articles are indexed", count);
        poolExecutor.shutdown();
        poolExecutor.awaitTermination(1, TimeUnit.DAYS);
        writer.commit();
        writer.close();
        connection.close();
        logger.info("process index over~~");
    }

    class Worker implements Runnable {
        Article article;

        public Worker(Article article) {
            this.article = article;
        }

        @Override
        public void run() {
            try {
                writer.addDocument(toDoc(article));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static Document toDoc(Article article) {
        Document document = new Document();
        if (!StringUtils.isEmpty(article.getTitle())) {
            document.add(new TextField(TITLE, article.getTitle().trim(), Field.Store.NO));
        }
        if (!StringUtils.isEmpty(article.getContent())) {
            document.add(new TextField(CONTENT, article.getContent().trim(), Field.Store.NO));
        }
        document.add(new TextField(ID, article.getId() + "", Field.Store.YES));

        return document;
    }


    public List<Article> fetchList(long min, int batch, String sql, Connection connection) throws SQLException {
        List<Article> array = new ArrayList<>();
        try (PreparedStatement psmt = connection.prepareStatement(sql);) {
            psmt.setLong(1, min);
            psmt.setInt(2, batch);
            try (ResultSet resultSet = psmt.executeQuery();) {
                while (resultSet.next()) {
                    String title = resultSet.getString("title");
                    String content = resultSet.getString("content");
                    long id = resultSet.getLong("id");
                    Article article = new Article();
                    article.setId(id);
                    article.setContent(content);
                    article.setTitle(title);
                    array.add(article);
                }
            }
        }
        return array;
    }

}
