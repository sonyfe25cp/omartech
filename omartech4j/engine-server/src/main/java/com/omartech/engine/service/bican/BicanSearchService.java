package com.omartech.engine.service.bican;

import cn.techwolf.data.gen.*;
import cn.techwolf.data.utils.DBUtils;
import com.omartech.engine.service.ADataService;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by omar on 14-12-16.
 */
public class BicanSearchService extends ADataService {

    static Logger logger = LoggerFactory.getLogger(BicanSearchService.class);

    public BicanSearchService(String indexPath) throws IOException {
        super(indexPath);
    }

    @Override
    public ArticleResponse searchArticle(ArticleRequest req) throws TException {

        String keyword = req.getKeyword();
        int offset = req.getOffset();
        int limit = req.getLimit();
        limit = limit == 0 ? 30 : limit;
        List<Long> ids = req.getIds();
        int topN = offset + limit;

        ArticleResponse response = new ArticleResponse();
        List<Article> articles = new ArrayList<>();
        Connection connection = fetchConnection("weixin");
        if (StringUtils.isEmpty(keyword)) {
            if (ids == null || ids.size() == 0) {
                logger.info("list the articles");
                articles = BicanDataService.findArticles(offset, limit, connection);
            } else {
                logger.info("find articles by ids ");
                for (long id : ids) {
                    Article art = BicanDataService.findById(id, connection);
                    articles.add(art);
                }
            }
        } else {
            logger.info("query:{}, offset:{}, limit:{}", new String[]{keyword, offset + "", limit + ""});
            try {
                BooleanQuery master = new BooleanQuery();
                List<String> words = cutWords(keyword);
                String[] fields = {TITLE, CONTENT};
                for (String field : fields) {
                    BooleanQuery bq = new BooleanQuery();
                    for (String word : words) {
                        TermQuery termQuery = new TermQuery(new Term(field, word));
                        bq.add(termQuery, BooleanClause.Occur.SHOULD);
                    }
                    master.add(bq, BooleanClause.Occur.SHOULD);
                }

                logger.info(master.toString());
                TopDocs topDocs = searcher.search(master, topN);
                ScoreDoc[] scoreDocs = topDocs.scoreDocs;
                int currentLength = scoreDocs.length;
                if (currentLength >= offset) {
                    for (ScoreDoc scoreDoc : scoreDocs) {
                        int docId = scoreDoc.doc;
                        Document doc = searcher.doc(docId);
                        String id = doc.get(ID);
                        Article article = BicanDataService.findById(Long.parseLong(id), connection);
                        articles.add(article);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        response.setArticles(articles);
        response.setKeyword(keyword);
        response.setTotal(topN);//todo:fix this bug
        logger.info("return {} articles for {}", articles.size(), keyword);
        return response;

    }

    @Override
    public ArticleResponse insertArticle(Article article) throws TException {
        logger.info("insert article :{}", article.toString());
        Connection connection = fetchConnection("weixin");
        article.setCreatedAt(formatTime(new Date()));
        try {
            BicanDataService.insert(article, connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        logger.info("insert over");
        return new ArticleResponse();
    }

    static Connection fetchConnection(String db) {
        Connection connection = null;
        boolean flag = false;
        do {
            switch (db) {
                case "weixin":
                    connection = con.get();
                    flag = DBUtils.verifyConnection(connection, "select id from rule limit 1");
                    if (!flag) {
                        con.remove();
                    }
                    break;
                case "beauty":
                    connection = beautyCon.get();
                    flag = DBUtils.verifyConnection(connection, "select id from images limit 1");
                    if (!flag) {
                        beautyCon.remove();
                    }
                    break;
                default:
                    logger.info("error db conn");
                    break;
            }
        } while (!flag);
        return connection;
    }

    static ThreadLocal<Connection> con = new InheritableThreadLocal<Connection>() {
        @Override
        protected Connection initialValue() {
            Connection conn = null;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/weixin", "root", "");
                logger.info("new connection to weixin");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return conn;
        }
    };
    static ThreadLocal<Connection> beautyCon = new InheritableThreadLocal<Connection>() {
        @Override
        protected Connection initialValue() {
            Connection conn = null;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/beauty", "root", "");
                logger.info("new connection to beauty");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return conn;
        }
    };

    @Override
    public BeautyResponse searchBeauty(BeautyRequest req) throws TException {
        String query = req.getQuery();
        int limit = req.getLimit();
        int offset = req.getOffset();
        String sql = "select id, thumbLargeUrl from images where 1=1 ";
        if (query != null) {
            sql += " and tags like '%" + query + "%' ";
        }
        sql += " limit ?,?";
        List<Beauty> beauties = new ArrayList<>();
        Connection connection = fetchConnection("beauty");
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1, offset);
            psmt.setInt(2, limit);
            try (ResultSet resultSet = psmt.executeQuery();) {
                logger.info("find beauty : {}", resultSet.getStatement().toString());
                while (resultSet.next()) {
                    String thumbLargeUrl = resultSet.getString("thumbLargeUrl");
                    Beauty beauty = new Beauty();
                    beauty.setThumbLargeUrl(thumbLargeUrl);
                    String id = resultSet.getString("id");
                    beauty.setId(Long.parseLong(id));
                    beauties.add(beauty);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        BeautyResponse response = new BeautyResponse();
        response.setBeauties(beauties);
        return response;
    }

    public static void main(String[] args) {
        try {
            BicanSearchService bicanSearchService = new BicanSearchService("bican");
            bicanSearchService.port = 5678;
            bicanSearchService.parseArgsAndRun(args);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
