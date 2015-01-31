package com.omartech.campusRobot.tools;

import com.google.gson.Gson;
import com.omartech.campusRobot.BITMember;
import com.omartech.campusRobot.bbsrobot.BITUnionRobot;
import org.apache.commons.io.FileUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by omar on 14-12-5.
 */
public class BITUnionMemberDownloader {
    static Logger logger = LoggerFactory.getLogger(BITUnionMemberDownloader.class);

    public static void main(String[] args) {
        BITUnionMemberDownloader bmd = new BITUnionMemberDownloader();
        try {
            bmd.gogo();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    void gogo() throws InterruptedException, IOException {

        CloseableHttpClient loginClient = BITUnionRobot.createLoginClient();

//        int begin = 1;
        List<BITMember> list = new ArrayList<>();
        for (int begin = 1; begin < 2524; begin++) {
            String memberList = "http://out.bitunion.org/member.php?action=list&srchmem=&order=regdate&page=" + begin;
            logger.info("begin to get {}", memberList);
            Thread.sleep(500);

            String listPage = BITUnionRobot.viewPage(loginClient, memberList);

//            System.out.println(listPage);
            try {
                FileUtils.write(new File("/tmp/bm/" + begin), listPage);
            } catch (IOException e) {
                e.printStackTrace();
            }

            int maxPage = 0;

            Element firstA = null;
            Document document = Jsoup.parse(listPage);
            Elements elements = document.select("a");
            for (Element element : elements) {
                String href = element.attr("href");
//            System.out.println(href);
                if (href.contains("profile-username-")) {
//                    logger.info("用户: {}", href);
                    if (firstA == null) {
                        firstA = element;
                    }
                }
                if (href.contains("member.php?action=list&srchmem=&order=regdate&page=")) {
                    String num = href.replace("member.php?action=list&srchmem=&order=regdate&page=", "");
                    int n = Integer.parseInt(num);
                    maxPage = Math.max(n, maxPage);
                }
            }
            logger.info("最大页数: {}", maxPage);

            Element trs = firstA.parent().parent().parent();
//            System.out.println(trs.html());


            for (Element tr : trs.select("tr")) {
                Elements tds = tr.select("td");
                if (tds != null && tds.size() > 0) {
                    Element td1 = tds.get(0);
                    String username = td1.text();
                    String link = td1.select("a").attr("href");

                    if (tds.size() > 1) {

                        Element td2 = tds.get(1);
                        if (td2 == null) {
                            continue;
                        }
                        String gender = td2.text();

                        Element td3 = tds.get(2);
                        String email = null;
                        Elements a = td3.select("a");
                        if (a != null && a.size() > 0) {
                            email = a.first().attr("href").replace("mailto:", "");
                        }

                        Element td4 = tds.get(3);
                        String homepage = null;
                        Elements pageA = td4.select("a");
                        if (pageA != null && pageA.size() > 0) {
                            homepage = pageA.first().select("a").attr("href");
                        }

                        Element td5 = tds.get(4);
                        String from = td5.text();

                        Element td6 = tds.get(5);
                        String regDate = td6.text();

                        Element td7 = tds.get(6);
                        String lastLogin = td7.text();

                        Element td8 = tds.get(7);
                        String postCount = td8.text();

                        Element td9 = tds.get(8);
                        String memScore = td9.text();

                        BITMember member = new BITMember();
                        member.setUsername(username);
                        member.setLink(link);
                        member.setGender(gender);
                        member.setEmail(email);
                        member.setHomepage(homepage);
                        member.setFrom(from);
                        member.setRegDate(regDate);
                        member.setLastLogin(lastLogin);
                        member.setPostCount(postCount);
                        member.setMemScore(memScore);
//                    System.out.println(member);
                        list.add(member);
                    } else {//no use
//                        logger.info(tr.html());
                    }
                }
            }
        }

        Gson gson = new Gson();
        String s = gson.toJson(list);
        try {
            FileUtils.write(new File("/tmp/bm/all.json"), s);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}