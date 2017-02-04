package com.omartech.engine.service.bican;

import com.omartech.data.gen.*;
import com.omartech.engine.service.ADataService;
import com.omartech.utils.DBUtils;
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
import java.util.Random;

/**
 * Created by omar on 14-12-16.
 */
public class BicanSearchService extends ADataService {

    static Logger logger = LoggerFactory.getLogger(BicanSearchService.class);

    public BicanSearchService(String indexPath) throws IOException {
        super(indexPath);
    }

    private static final String CAMPUS = "campus";
    private static final String BEAUTY = "beauty";
    private static final String WEIXIN = "weixin";

    @Override
    public ArticleResponse searchArticle(ArticleRequest req) throws TException {

        String keyword = req.getKeyword();
        int offset = req.getOffset();
        int limit = req.getLimit();
        limit = limit == 0 ? 30 : limit;
        List<Long> ids = req.getIds();
        boolean isRandom = req.isRandom();
        int topN = offset + limit;
        ArticleType articleType = req.getArticleType();

        logger.info(req.toString());

        ArticleResponse response = new ArticleResponse();
        List<Article> articles = new ArrayList<>();
        Connection connection = fetchConnection("weixin");
        if (StringUtils.isEmpty(keyword)) {
            if (ids == null || ids.size() == 0) {
                if (isRandom) {//随机选一个
                    int maxDoc = searcher.getIndexReader().maxDoc();
                    do {
                        Random rand = new Random();
                        int pos = Math.round(rand.nextFloat() * maxDoc);
                        try {
                            Document doc = searcher.doc(pos);
                            String id = doc.get(ID);
                            Article article = BicanDataService.findById(Long.parseLong(id), connection);
                            if (article != null) {
                                logger.info("random choose article id : {}", id);
                                int length = article.getContent().length();
                                if (length > 10) {
                                    articles.add(article);
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } while (articles.size() != limit);
                    response.setArticles(articles);
                } else {
                    logger.info("list the articles");
                    if (articleType == null) {
                        articles = BicanDataService.findArticles(offset, limit, connection);
                        logger.info("articles,size:{}", articles.size());
                    } else {
                        TermQuery termQuery = new TermQuery(new Term("appName", articleType.toString()));
                        try {
                            TopDocs topDocs = searcher.search(termQuery, topN);
                            int totalHits = topDocs.totalHits;
                            logger.info("totoal : {}", totalHits);
                            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
                            int currentLength = scoreDocs.length;
                            logger.info("currentLengt:{}", currentLength);
                            if (currentLength > 0) {
                                if (offset == -1) {//随机来一发
                                    Random random = new Random();
                                    offset = Math.round(random.nextFloat() * currentLength);
                                }
                                int end = currentLength > (offset + limit) ? (offset + limit) : currentLength;
                                for (int pos = offset; pos < end; pos++) {
                                    int docId = scoreDocs[pos].doc;
                                    Document doc = searcher.doc(docId);
                                    String id = doc.get(ID);
                                    Article article = BicanDataService.findById(Long.parseLong(id), connection);
                                    if (article != null) {
                                        String content = article.getContent();
                                        if (content.length() > 10) {
                                            articles.add(article);
                                        }
                                    }
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                logger.info("find articles by ids ");
                for (long id : ids) {
                    Article article = BicanDataService.findById(id, connection);
                    if (article != null) {
                        articles.add(article);
                    }
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
                int totalHits = topDocs.totalHits;
                response.setTotal(totalHits);
                ScoreDoc[] scoreDocs = topDocs.scoreDocs;
                int currentLength = scoreDocs.length;
                if (currentLength > 0) {
                    int end = currentLength > offset ? offset : currentLength;
                    if (currentLength >= offset) {
                        for (int pos = offset; pos <= end; pos++) {
                            int docId = scoreDocs[pos].doc;
                            Document doc = searcher.doc(docId);
                            String id = doc.get(ID);
                            Article article = BicanDataService.findById(Long.parseLong(id), connection);
                            if (article != null) {
                                articles.add(article);
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        response.setArticles(articles);
        response.setKeyword(keyword);
        logger.info("return {} articles for {}", articles.size(), keyword);
        return response;

    }

    @Override
    public ArticleResponse insertArticle(Article article) throws TException {
        Connection connection = fetchConnection("weixin");
        if (StringUtils.isEmpty(article.getCreatedAt())) {
            article.setCreatedAt(formatTime(new Date()));
        }
        try {
            logger.info("insert article :{}", article.toString());
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
                case WEIXIN:
                    connection = con.get();
                    flag = DBUtils.verifyConnection(connection, "select id from rule limit 1");
                    if (!flag) {
                        con.remove();
                    }
                    break;
                case BEAUTY:
                    connection = beautyCon.get();
                    flag = DBUtils.verifyConnection(connection, "select id from images limit 1");
                    if (!flag) {
                        beautyCon.remove();
                    }
                    break;
                case CAMPUS:
                    connection = campusCon.get();
                    flag = DBUtils.verifyConnection(connection, "select id from jd limit 1");
                    if (!flag) {
                        campusCon.remove();
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
    static ThreadLocal<Connection> campusCon = new InheritableThreadLocal<Connection>() {
        @Override
        protected Connection initialValue() {
            Connection conn = null;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/campus", "root", "");
                logger.info("new connection to campus");
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
        sql += " order by rand() limit ?,?";
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

    @Override
    public JobResponse searchJobs(JobRequest req) throws TException {
        String createdAt = req.getCreatedAt();
        int limit = req.getLimit();
        int offset = req.getOffset();
        String word = req.getWord();
        String jobType = req.getJobType();
        String publishDate = req.getPublishDate();
        String area = req.getArea();
        JobResponse jobResponse = new JobResponse();
        jobResponse.setOffset(offset);
        jobResponse.setWord(word);
        jobResponse.setArea(area);
        List<Job> jobs = new ArrayList<>();
        if (!StringUtils.isEmpty(createdAt)) {//按日期查找
            Connection connection = fetchConnection(CAMPUS);
            String sql = "SELECT id, title, url, siteName, industry, hrEmail, company FROM jd WHERE createdAt >= ? ";
            if (!StringUtils.isEmpty(jobType)) {
                sql += " and jobType = '实习'";
            } else {
                sql += " and jobType != '实习'";
            }
            if (!StringUtils.isEmpty(area)) {
                sql += " and area like '%" + area + "%' ";
            }
            sql += "order by title LIMIT ?, ?";
            logger.info("jobs sql: {}", sql);
            try (
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ) {
                preparedStatement.setString(1, createdAt);
                preparedStatement.setInt(2, offset);
                preparedStatement.setInt(3, limit);
                try (ResultSet resultSet = preparedStatement.executeQuery();) {
                    while (resultSet.next()) {
                        long id = resultSet.getLong("id");
                        String title = resultSet.getString("title");
                        String url = resultSet.getString("url");
                        String siteName = resultSet.getString("siteName");
                        String industry = resultSet.getString("industry");
                        String hrEmail = resultSet.getString("hrEmail");
                        String company = resultSet.getString("company");
                        Job job = new Job();
                        job.setTitle(title);
                        job.setCreatedAt(createdAt);
                        job.setPublishDate(publishDate);
                        job.setHrEmail(hrEmail);
                        job.setId(id);
                        job.setCompany(company);
                        job.setSiteName(siteName);
                        job.setUrl(url);
                        job.setIndustry(industry);
                        jobs.add(job);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            logger.error("其他需求?");
        }
        jobResponse.setJobs(jobs);
        jobResponse.setTotal(jobs.size());
        logger.info("search createdAt:{}, publishDate:{}, area:{}, offset:{}, limit:{}, jobType:{}, jobs:{}", new String[]{createdAt, publishDate, area, offset + "", limit + "", jobType, jobs.size() + ""});
        return jobResponse;
    }


    @Override
    public ArticleResponse increaseArticleHot(Article article) throws TException {
        long id = article.getId();
        try (Connection connection = fetchConnection("weixin");) {
            article = BicanDataService.findById(id, connection);
            int hot = article.getHot();
            hot = hot + 1;
            BicanDataService.updateArticleHot(id, hot, connection);
            logger.info("update hot, id:{}, hot:{}", id, hot);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArticleResponse();
    }

    @Override
    public ArticleResponse decreaseArticleHot(Article article) throws TException {
        long id = article.getId();
        try (Connection connection = fetchConnection("weixin");) {
            article = BicanDataService.findById(id, connection);
            int hot = article.getHot();
            hot = hot - 1;
            BicanDataService.updateArticleHot(id, hot, connection);
            logger.info("update hot, id:{}, hot:{}", id, hot);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArticleResponse();
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
