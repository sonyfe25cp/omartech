package com.omartech.laibicanRobot.service;

import com.omartech.engine.client.DataClients;
import com.omartech.laibicanRobot.filter.Rule;
import com.omartech.laibicanRobot.filter.RuleType;
import com.omartech.laibicanRobot.model.ReplyEnum;
import com.omartech.laibicanRobot.model.User;
import com.omartech.laibicanRobot.model.reply.ArticleReply;
import com.omartech.laibicanRobot.model.reply.ArticleReplyItem;
import com.omartech.laibicanRobot.model.reply.NormalReply;
import com.omartech.laibicanRobot.model.reply.ReplyMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;

/**
 * Created by omar on 14-12-18.
 */
public class CenterService {
    static Logger logger = LoggerFactory.getLogger(CenterService.class);


    public static DataClients fetchClient() {
        DataClients dataClients = new DataClients("127.0.0.1:5678,127.0.0.1:5678");
        return dataClients;
    }


    public ReplyMessage findAnswer(String query) throws SQLException {
        ReplyMessage replyMessage = null;

        replyMessage = matchFilter(query);
        if (replyMessage == null) {
            String keyWord = findKeyWord(query);

            //DataClient client = new DataClient();
            //Article article = client.search(keyWord);
            //reply = Wrapper.wrap();
        }
        if (replyMessage == null) {
            return fetchBakUpMsg();
        }
        return replyMessage;
    }

    private ReplyMessage fetchBakUpMsg() {
        NormalReply normalReply = new NormalReply();
        normalReply.setContent("嗯，然后呢？");
        return normalReply;
    }

    private String findKeyWord(String query) {
        return null;
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
        Connection connection = con.get();
        List<ReplyMessage> list = new ArrayList<>();
        String sql = "select * from replys where flag = 1 limit ?,?";
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
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
        String sql = "select * from replys where id = ?";
        Connection connection = con.get();
        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
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
        Connection connection = con.get();
        String sql = "select * from rule";
        try (PreparedStatement psmt = connection.prepareStatement(sql);
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
        Connection connection = con.get();
        List<Rule> rules = new ArrayList<>();
        String sql = "";
        if (limit == 0) {
            sql = "select id, ruleType, keyword, replyId from rules";
        } else {
            sql = "select id, ruleType, keyword, replyId from rules limit " + offset + "," + limit;
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
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


    public void insertUser(User user, Connection connection) throws SQLException {
        String sql = "insert into users(uid, subscribe, nickname, sex, city, country, province, language, headimgurl, subscribe_time, unionid) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
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
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return conn;
        }
    };

}
