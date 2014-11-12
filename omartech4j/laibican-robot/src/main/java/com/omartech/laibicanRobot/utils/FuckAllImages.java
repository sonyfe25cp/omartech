package com.omartech.laibicanRobot.utils;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by omar on 14/11/12.
 */
public class FuckAllImages {

    static Logger logger = LoggerFactory.getLogger(FuckAllImages.class);
    public static void main(String[] args) {
        String path = "/tmp/1115.html";
        try {
            fuck(path);
        } catch (IOException e) {

        }
    }

    static void fuck(String path) throws IOException {
        String content = FileUtils.readFileToString(new File(path));

        Pattern pattern = Pattern.compile("(http://.*.[png|jpg])");

        Matcher matcher = pattern.matcher(content);
        while(matcher.find()){
//            logger.info(matcher.group());
            System.out.println(matcher.group());
        }

    }
}
