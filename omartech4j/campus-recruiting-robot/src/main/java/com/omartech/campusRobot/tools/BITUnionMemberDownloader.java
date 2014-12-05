package com.omartech.campusRobot.tools;

import com.omartech.campusRobot.bbsrobot.BITUnionRobot;
import org.apache.http.impl.client.CloseableHttpClient;

/**
 * Created by omar on 14-12-5.
 */
public class BITUnionMemberDownloader {


    public static void main(String[] args) {

    }

    void gogo(){

        CloseableHttpClient loginClient = BITUnionRobot.createLoginClient();

        int begin = 1;
        String memberList = "http://out.bitunion.org/member.php?action=list&srchmem=&order=regdate&page="+begin;

        String listPage = BITUnionRobot.viewPage(loginClient, memberList);

        System.out.println(listPage);


    }

}
