package com.omartech.campusRobot.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import cn.techwolf.data.gen.Job;
import com.omartech.campusRobot.bbsrobot.BITUnionRobot;
import com.omartech.campusRobot.service.JDService;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.omartech.campusRobot.template.BITUnionPoster;

public class AutoPoster {
    static Logger logger = LoggerFactory.getLogger(AutoPoster.class);

    public static void main(String[] args) {
        AutoPoster ap = new AutoPoster();
        try {
            ap.gogogo();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void gogogo() throws SQLException {
        String today = DateFormatUtils.format(new Date(), "yyyy-MM-dd 截止HH点");
//		today = "2014-10-30";

        logger.info(today);


        List<Job> list = JDService.findByDate(today, con.get());

        logger.info("list size : {}, at {}", list.size(), today);

        if (list.size() == 0) {
            logger.info("no jd today");
            return;
        }

        String listHtml = BITUnionPoster.create(list);

//		logger.info(listHtml);
        logger.info("size : {}", listHtml.length());

        listHtml = listHtml + "蛋疼的汇总贴..." +
                "花了4个多小时整理的代码，如果对大家有帮助，请回帖支持下；" +
                "若觉得无用，也请回帖吐槽下。" +
                "\n将根据意见决定保持每日更新or暂停蛋疼行为。" +
                "\n[size=5][color=Red]请扫描头像的二维码 或者 公众号搜 job-seeker[/color][/size]:)";

        String title = "【" + today + "】【今日校招信息汇总】按照来源排序";

        BITUnionRobot.publishPost(title, listHtml, 83);

    }

    static ThreadLocal<Connection> con = new InheritableThreadLocal<Connection>() {
        @Override
        protected Connection initialValue() {
            Connection conn = null;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/campus", "root", "");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return conn;
        }
    };


}
