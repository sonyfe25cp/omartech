package com.omartech.laibicanRobot.service;

import com.omartech.data.gen.*;
import com.omartech.engine.client.ClientException;
import com.omartech.engine.client.DataClients;
import com.omartech.laibicanRobot.filter.Rule;
import com.omartech.laibicanRobot.filter.RuleType;
import com.omartech.laibicanRobot.model.AppEnum;
import com.omartech.laibicanRobot.model.QueryLog;
import com.omartech.laibicanRobot.model.ReplyEnum;
import com.omartech.laibicanRobot.model.User;
import com.omartech.laibicanRobot.model.message.WeixinEventMessage;
import com.omartech.laibicanRobot.model.message.WeixinTextMessage;
import com.omartech.laibicanRobot.model.reply.ArticleReply;
import com.omartech.laibicanRobot.model.reply.ArticleReplyItem;
import com.omartech.laibicanRobot.model.reply.NormalReply;
import com.omartech.laibicanRobot.model.reply.ReplyMessage;
import com.omartech.utils.DBUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * Created by omar on 14-12-18.
 */

public class CenterService {
    static Logger logger = LoggerFactory.getLogger(CenterService.class);


    private static DataClients dataClients = new DataClients("127.0.0.1:5678,127.0.0.1:5678");

    public static DataClients fetchClient() {
        return dataClients;
    }


    Connection fetchConnection() {
        Connection connection;
        boolean flag = false;
        do {
            connection = con.get();
            flag = DBUtils.verifyConnection(connection, "select id from rule limit 1");
            if (!flag) {
                con.remove();
            }
        } while (!flag);
        return connection;
    }


    public ReplyMessage findAnswer(WeixinTextMessage textMessage) throws SQLException, ClientException {
        String query = textMessage.getContent();
        String fromName = textMessage.getFromName();
        String toName = textMessage.getToName();
        int date10 = textMessage.getDate10();
        long messageId = textMessage.getMessageId();


        AppEnum appEnum = textMessage.getAppEnum();
        if (appEnum == null) {
            logger.error("some app miss appEnum");
        }
        insertQueryLog(fromName, query, appEnum.toString(), fetchConnection());

        ReplyMessage replyMessage = null;


        if (query.contains("求签")) {
//            replyMessage = fetchBakUpMsg("http://www.laibican.com/sactivity/yaoqian.html");
            replyMessage = fetchYaoqianMsg();
        } else if (query.startsWith("没了") || query.startsWith("没有然后'") || query.startsWith("没有了") || query.startsWith("没啦")) {
            replyMessage = fetchBakUpMsg("噢...");
        } else if (query.contains("相亲宝典")) {
            replyMessage = fetchXiangqin();
        } else if (query.contains("自动回复")) {
            replyMessage = fetchBakUpMsg("哎，毕竟小编不能一天到晚守在电脑前。不过你发个照片，神龙会召唤小编的~");
        } else {
            replyMessage = matchFilter(query);
            if (replyMessage == null) {
                logger.info("find reply from index");
                String keyWord = findKeyWord(query);
                DataClients dataClients = fetchClient();
                ArticleRequest req = new ArticleRequest();
                req.setKeyword(keyWord);
                ArticleResponse articleResponse = dataClients.searchArticle(req);
                replyMessage = wrap(articleResponse, appEnum);
            }
        }
        return replyMessage;
    }

    private static ReplyMessage fetchXiangqin() {
        ArticleReplyItem a1 = new ArticleReplyItem();
        ArticleReplyItem a2 = new ArticleReplyItem();
        ArticleReplyItem a3 = new ArticleReplyItem();
        a1.setTitle("相亲宝典：第一部");
        a1.setPicUrl("https://mmbiz.qlogo.cn/mmbiz/Q2BricGyedbgxgo2WKMECkIm69RicpKLzH1dQ3gZdL1fiaSVkdjCq4CKBklIftZsGgWWO5Ue3mmAIsu06aAwjHjcg/0");
        a2.setPicUrl("https://mmbiz.qlogo.cn/mmbiz/Q2BricGyedbjwZ9fJXOKiaew1EvG7XicOXQ9E0rngCfk0mvQUmQ5V1vlNV4ib4LQVvKyeibjdqnia8htN1vF6nyjk7Ow/0");
        a3.setPicUrl("https://mmbiz.qlogo.cn/mmbiz/Q2BricGyedbhUkKzW8kEy1UBtYTbUgeVaEpy2RjTRH1ocOMBrhIPZaKpwPAMrpPmRahtzXnrJMWlZgPt2uqxia3Q/0");
        a1.setUrl("http://mp.weixin.qq.com/s?__biz=MzAxNTA5MTc1NQ==&mid=203168325&idx=2&sn=277655df7526a6ee2843656720537a8b#rd");
        a2.setUrl("http://mp.weixin.qq.com/s?__biz=MzAxNTA5MTc1NQ==&mid=203189676&idx=2&sn=812423b80c5be97fe8ce9e8d6296ec78#rd");
        a3.setUrl("http://mp.weixin.qq.com/s?__biz=MzAxNTA5MTc1NQ==&mid=203207457&idx=2&sn=b811abbdfb14647b7904813c6aa1cfab#rd");
        a2.setTitle("相亲宝典：第二部");
        a3.setTitle("相亲宝典：第三部");

        ArticleReply ar = new ArticleReply();
        ar.addArticleReplyItem(a1);
        ar.addArticleReplyItem(a2);
        ar.addArticleReplyItem(a3);
        return ar;

    }

    public ReplyMessage findAnswer(String uid, String query, AppEnum appEnum, String toName) throws SQLException, ClientException {

        if (appEnum == null) {
            logger.error("some app miss appEnum");
        }
        insertQueryLog(uid, query, appEnum.toString(), fetchConnection());
        ReplyMessage replyMessage = null;

        replyMessage = matchFilter(query);
        if (replyMessage == null) {
            logger.info("find reply from index");
            String keyWord = findKeyWord(query);
            DataClients dataClients = fetchClient();
            ArticleRequest req = new ArticleRequest();
            req.setKeyword(keyWord);
            req.setLimit(10);
            ArticleResponse articleResponse = dataClients.searchArticle(req);
            replyMessage = wrap(articleResponse, appEnum);
        }
        if (replyMessage == null) {
            logger.info("reply with default");
            replyMessage = fetchBakUpMsg("嗯，然后呢？");
        }

        replyMessage.setFromName(toName);
        replyMessage.setToName(uid);
        replyMessage.setAddTime(new Date());
        logger.info("reply msg to client");
        return replyMessage;
    }


    private ReplyMessage wrap(ArticleResponse articleResponse, AppEnum appEnum) {
        List<Article> articles = articleResponse.getArticles();
        if (articles == null || articles.size() == 0) {
            logger.info("wrap no articles");
            return null;
        } else {
            ArticleReply replyMessage = new ArticleReply();

            for (Article article : articles) {
                ArticleReplyItem articleReplyItem = new ArticleReplyItem();
                ArticleType articleType = article.getArticleType();
                String title = article.getTitle();
                if (title == null || title.trim().length() == 0) {
                    String content = article.getContent();
                    if (content.length() > 15) {
                        title = content.substring(0, 15) + "..";
                    } else {
                        title = content;
                    }
                    switch (articleType) {
//                        case Xiaohua:
//                            title = "笑话" + article.getId();
//                            break;
                        case Bican:
                            title = "【我好惨啊】" + title;
                            break;
//                        case Shudong:
//                            title = "树洞" + article.getId();
//                            break;
//                        case Jobs:
//                            title = "招聘" + article.getId();
//                            break;
                        default:
                            title = title;
                    }
                }
                articleReplyItem.setTitle(title);
                articleReplyItem.setDescription(article.content);
                articleReplyItem.setPicUrl(findAPicture());
                articleReplyItem.setUrl(wrapUrl(article.getId(), appEnum));
                replyMessage.addArticleReplyItem(articleReplyItem);
            }
            return replyMessage;
        }

    }

    private String wrapUrl(long id, AppEnum appEnum) {
        String host = "http://www.laibican.com";
        if (appEnum == null) {
            appEnum = AppEnum.Other;
        }
        String url = host + "/wx/" + appEnum.toString() + "-" + id + ".html";
        return url;
    }

    public String findAPicture() {
        Random random = new Random();
        int i1 = random.nextInt();
        int abs = Math.abs(i1);
        int i = abs % 30000;
        DataClients dataClients = fetchClient();
        BeautyRequest request = new BeautyRequest();
        request.setOffset(i);
        request.setLimit(1);
        String url = "";
        try {
            BeautyResponse beautyResponse = dataClients.searchBeauty(request);
            List<Beauty> beauties = beautyResponse.getBeauties();
            for (Beauty beauty : beauties) {
                url = beauty.getThumbLargeUrl();
            }
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return url;
    }

    private ReplyMessage fetchYaoqianMsg() {
        String url = "http://www.laibican.com/sactivity/yaoqian.html";//新年抽签
        String pic = "http://mmbiz.qpic.cn/mmbiz/Q2BricGyedbg9ziajFTlJoJ2PIlFQqAbsOibvGvkQJbeKLRaU6LvyeH5pOS4VsY1lrNACibjuV3cYTTqIwRFOkB0oA/0?tp=webp";
        ArticleReplyItem articleReplyItem = new ArticleReplyItem();
        articleReplyItem.setUrl(url);
        articleReplyItem.setPicUrl(pic);
        articleReplyItem.setTitle("为2015抽一发幸运签吧！");
        articleReplyItem.setDescription("2015羊年到，抽一卦幸运签，好运先知道，让朋友也来卜一下吧。");

        ArticleReply articleReply = new ArticleReply();
        articleReply.addArticleReplyItem(articleReplyItem);
        return articleReply;
    }

    public static ReplyMessage fetchBakUpMsg(String str) {
        NormalReply normalReply = new NormalReply();
        normalReply.setContent(str);
        return normalReply;
    }

    private String findKeyWord(String query) {
        return query;
    }

    ReplyMessage matchFilter(String query) throws SQLException {
        ReplyMessage replyMessage = null;
        List<Rule> rules = fetchRules(0, 0);
        final Map<RuleType, Integer> orderMap = fetchRuleOrder();
        Collections.sort(rules, new Comparator<Rule>() {
            @Override
            public int compare(Rule o1, Rule o2) {
                RuleType ruleType = o1.getRuleType();
                RuleType ruleType1 = o2.getRuleType();
                int ord1 = orderMap.get(ruleType);
                int ord2 = orderMap.get(ruleType1);
                return ord1 - ord2;
            }
        });
        for (int i = 0; i < rules.size(); i++) {
            Rule rule = rules.get(i);
            boolean flag = rule.match(query);
            if (flag) {
                int replyId = rule.getReplyId();
                replyMessage = fetchReplyMessageById(replyId);
                break;
            }
        }
        return replyMessage;
    }

    public List<ReplyMessage> listReplyMessage(int begin, int limit) throws SQLException {
        List<ReplyMessage> list = new ArrayList<>();
        String sql = "SELECT * FROM replys WHERE flag = 1 LIMIT ?,?";
        try (PreparedStatement psmt = fetchConnection().prepareStatement(sql)) {
            psmt.setInt(1, begin);
            psmt.setInt(2, limit);
            try (ResultSet resultSet = psmt.executeQuery();) {
                while (resultSet.next()) {
                    String replyEnum = resultSet.getString("replyEnum");
                    String title = resultSet.getString("title");
                    String content = resultSet.getString("content");
                    int id = resultSet.getInt("id");
                    ReplyMessage replyMessage = new ArticleReply();
                    replyMessage.setContent(content);
                    replyMessage.setId(id);
                    list.add(replyMessage);
                }
            }
        }

        List<ReplyMessage> array = new ArrayList<>();
        for (ReplyMessage replyMessage : list) {
            int id = replyMessage.getId();
            ReplyMessage replyMessage1 = fetchReplyMessageById(id);
            array.add(replyMessage1);
        }
        return array;
    }


    private ReplyMessage fetchReplyMessageById(int replyId) throws SQLException {
        String sql = "SELECT * FROM replys WHERE id = ?";
        try (PreparedStatement psmt = fetchConnection().prepareStatement(sql)) {
            psmt.setInt(1, replyId);
            try (ResultSet resultSet = psmt.executeQuery();) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String ruleTypeStr = resultSet.getString("replyEnum");
                    ReplyEnum replyEnum = ReplyEnum.valueOf(ruleTypeStr);
                    String title = resultSet.getString("title");
                    String description = resultSet.getString("description");
                    String url = resultSet.getString("url");
                    String picUrl = resultSet.getString("picUrl");
                    String content = resultSet.getString("content");
                    String array = resultSet.getString("array");
                    switch (replyEnum) {
                        case Text:
                            NormalReply replyMessage = new NormalReply();
                            replyMessage.setContent(content);
                            return replyMessage;
                        case Article:
                            ArticleReplyItem articleReplyItem = new ArticleReplyItem();
                            articleReplyItem.setTitle(title);
                            articleReplyItem.setDescription(description);
                            articleReplyItem.setUrl(url);
                            articleReplyItem.setPicUrl(picUrl);
                            return articleReplyItem;
                        case List:
                            String[] split = array.split(",");
                            ArticleReply articleReply = new ArticleReply();
                            for (String str : split) {
                                int num = Integer.parseInt(str);
                                ReplyMessage rm = fetchReplyMessageById(num);
                                ArticleReplyItem ar = (ArticleReplyItem) rm;
                                articleReply.addArticleReplyItem(ar);
                            }
                            return articleReply;
                        default:
                            return null;
                    }

                } else {
                    logger.info("not found reply with id : {}", replyId);
                    return null;
                }
            }
        }
    }


    private Map<RuleType, Integer> fetchRuleOrder() throws SQLException {
        Map<RuleType, Integer> map = new HashMap<>();
        String sql = "SELECT * FROM rule";
        try (PreparedStatement psmt = fetchConnection().prepareStatement(sql);
             ResultSet rs = psmt.executeQuery();
        ) {
            while (rs.next()) {
                String ruleTypeStr = rs.getString("ruleType");
                RuleType ruleType = RuleType.valueOf(ruleTypeStr);
                int priority = rs.getInt("priority");
                map.put(ruleType, priority);
            }
        }
        return map;
    }

    public List<Rule> fetchRules(int offset, int limit) throws SQLException {
        List<Rule> rules = new ArrayList<>();
        String sql = "";
        if (limit == 0) {
            sql = "SELECT id, ruleType, keyword, replyId FROM rules";
        } else {
            sql = "select id, ruleType, keyword, replyId from rules limit " + offset + "," + limit;
        }
        try (PreparedStatement preparedStatement = fetchConnection().prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery();
        ) {
            while (resultSet.next()) {
                String ruleTypeStr = resultSet.getString("ruleType");
                RuleType ruleType = RuleType.valueOf(ruleTypeStr);

                String keyword = resultSet.getString("keyword");
                int rid = resultSet.getInt("replyId");

                Rule rule = new Rule();
                rule.setRuleType(ruleType);
                rule.setWord(keyword);
                rule.setReplyId(rid);
                rules.add(rule);
            }
        }

        return rules;
    }

    public List<QueryLog> findQueryLogs(int offset, int limit) {
        return findQueryLogs(offset, limit, con.get());
    }

    public List<QueryLog> findQueryLogs(int offset, int limit, Connection connection) {
        List<QueryLog> logs = new ArrayList<>();
        String sql = "SELECT id, uid, query, source FROM logs LIMIT ?,? ";
        try {
            PreparedStatement psmt = connection.prepareStatement(sql);
            psmt.setInt(1, offset);
            psmt.setInt(2, limit);
            ResultSet rs = psmt.executeQuery();
            while (rs.next()) {
                QueryLog object = new QueryLog();
                int id = rs.getInt("id");
                object.setId(id);
                String uid = rs.getString("uid");
                object.setUid(uid);
                String query = rs.getString("query");
                object.setQuery(query);
                String source = rs.getString("source");
                object.setSource(source);
                logs.add(object);
            }
            rs.close();
            psmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logs;
    }

    public void insertQueryLog(String openId, String query, String appName, Connection connection) throws SQLException {
        String sql = "INSERT INTO logs(uid, query, source) VALUES(?,?,?)";
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1, openId);
            psmt.setString(2, query);
            psmt.setString(3, appName);
            psmt.executeUpdate();
        }
        logger.info("insert query log over");
    }


    public void insertUser(User user, Connection connection) throws SQLException {
        String sql = "INSERT INTO users(uid, subscribe, nickname, sex, city, country, province, language, headimgurl, subscribe_time, unionid) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        int subscribe = user.getSubscribe();
        String nickname = user.getNickname();
        int sex = user.getSex();
        String city = user.getCity();
        String country = user.getCountry();
        String province = user.getProvince();
        String language = user.getLanguage();
        String headimgurl = user.getHeadimgurl();
        int subscribe_time = user.getSubscribe_time();
        String unionid = user.getUnionid();
        try {
            PreparedStatement psmt = connection.prepareStatement(sql);
            psmt.setString(1, user.getUid());//uid
            psmt.setInt(2, subscribe);//subscribe
            psmt.setString(3, nickname);//nickname
            psmt.setInt(4, sex);//sex
            psmt.setString(5, city);//city
            psmt.setString(6, country);//country
            psmt.setString(7, province);//province
            psmt.setString(8, language);//language
            psmt.setString(9, headimgurl);//headimgurl
            psmt.setInt(10, subscribe_time);//subscribe_time
            psmt.setString(11, unionid);//unionid
            psmt.execute();
            psmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    static ThreadLocal<Connection> con = new InheritableThreadLocal<Connection>() {
        @Override
        protected Connection initialValue() {
            Connection conn = null;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/weixin", "root", "");
                logger.info("open a new connection to db");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return conn;
        }
    };

    public ReplyMessage findResponseWith(WeixinEventMessage eventMessage) {
        AppEnum appEnum = eventMessage.getAppEnum();
        String eventKey = eventMessage.getEventKey();
        switch (appEnum) {
            case Bican:

                break;
            default:
                switch (eventKey) {
                    case "beauty"://看个美女
                        BeautyRequest bq = new BeautyRequest();
                        int max = 36860;
                        Random random = new Random();
                        float rate = random.nextFloat();
                        int offset = Math.round(max * rate);
                        bq.setOffset(offset);
                        bq.setLimit(1);
                        try {
                            BeautyResponse response = dataClients.searchBeauty(bq);
                            List<Beauty> beauties = response.getBeauties();
                            ArticleReply articleReply = new ArticleReply();
                            for (Beauty beauty : beauties) {
                                ArticleReplyItem articleReplyItem = new ArticleReplyItem();
                                articleReplyItem.setTitle(beauty.getTags() + "美女一枚");
                                articleReplyItem.setPicUrl(beauty.getThumbLargeUrl());
                                articleReplyItem.setUrl("http://www.laibican.com/beauty/" + beauty.getId());
                                articleReplyItem.setDescription("这里还有很多很棒的模特~");
                                articleReply.addArticleReplyItem(articleReplyItem);
                            }
                            return articleReply;
                        } catch (ClientException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "bican":
                        ArticleRequest articleRequest = new ArticleRequest();
                        articleRequest.setArticleType(ArticleType.Bican);
                        articleRequest.setLimit(1);
                        articleRequest.setOffset(-1);
                        try {
                            ArticleResponse bicanResponse = dataClients.searchArticle(articleRequest);
                            List<Article> articles = bicanResponse.getArticles();
                            NormalReply normalReply = new NormalReply();
                            for (Article article : articles) {
                                normalReply.setContent(article.getContent());
                            }
                            return normalReply;
                        } catch (ClientException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "jok":
                        ArticleRequest jokRequest = new ArticleRequest();
                        jokRequest.setArticleType(ArticleType.Xiaohua);
                        jokRequest.setLimit(1);
                        jokRequest.setOffset(-1);
                        try {
                            ArticleResponse jokResponse = dataClients.searchArticle(jokRequest);
                            List<Article> articles = jokResponse.getArticles();
                            NormalReply normalReply = new NormalReply();
                            for (Article article : articles) {
                                normalReply.setContent(article.getContent());
                            }
                            return normalReply;
                        } catch (ClientException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "advice":
                        return fetchBakUpMsg("请给小编些建议或者抱怨吧:)");
                    case "love":
                        return fetchXiangqin();
                    case "lucky":
                        return fetchYaoqianMsg();
                    case "money":
                        return fetchMoneyMsg();

                    default:
                        logger.info("what?{}", eventKey);
                        break;
                }
                break;
        }
        return null;
    }

    static ReplyMessage fetchMoneyMsg() {
        ArticleReply articleReply = new ArticleReply();
        ArticleReplyItem articleReplyItem = new ArticleReplyItem();
        articleReplyItem.setTitle("微信发红包");
        articleReplyItem.setUrl("http://mp.weixin.qq.com/s?__biz=MzAxNTA5MTc1NQ==&mid=203362192&idx=1&sn=990cd09d8886ad4b019c3ab4b5ad6f74#rd");
        articleReplyItem.setPicUrl("https://mmbiz.qlogo.cn/mmbiz/Q2BricGyedbjGessTtOlNicrKkNkMxmyo5xpibryrnSiciaNhTzVzhrbDtQzibzp8knMOnhJ2o9Vx3sK8W3Ju2HDmicKA/0");
        articleReplyItem.setDescription("恭喜发财！领取红包>>");
        articleReply.addArticleReplyItem(articleReplyItem);
        return articleReply;
    }

    public JobResponse findJobsToday(String today, boolean intern, String area, int offset, int limit) {
        JobRequest req = new JobRequest();
        req.setOffset(offset);
        req.setLimit(limit);
        req.setCreatedAt(today);
        req.setArea(area);
        if (intern) {
            req.setJobType("实习");
        }
        JobResponse jobResponse = null;
        try {
            jobResponse = dataClients.searchJobs(req);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return jobResponse;
    }
}
