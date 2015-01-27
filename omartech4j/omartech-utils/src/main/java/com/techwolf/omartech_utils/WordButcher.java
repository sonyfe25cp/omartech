
package com.techwolf.omartech_utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * 句子切割机
 *
 * @author Chen Jie
 * @date 30 Aug, 2014
 */
public class WordButcher {

    static Logger logger = LoggerFactory.getLogger(WordButcher.class);

    public static int defaultMaxLength = 7;

    public static List<String> cutSentence(String str, int wordLength, int maxLength) {
        List<String> array = new ArrayList<>();
        if (str == null || str.trim().length() == 0) {
            return array;
        } else if (str.length() < wordLength) {
            return array;
        }
        StringBuilder sb = new StringBuilder();
        for (String tmp : stopArray) {
            sb.append(tmp);
        }
//        logger.debug(sb.toString());
        String[] split = str.split("[" + sb.toString() + "]");
        for (String sub : split) {
            logger.debug(sub + " == >");
            if (sub.length() > 3) {
                for (int end = sub.length(); end >= 3; end--) {
                    for (int i = sub.length() - 3; i >= 0; i--) {
                        int abs = end - i;
                        if (abs >= wordLength && abs <= maxLength) {
                            try {
                                String tmp = sub.substring(i, end);
                                if (tmp.trim().length() > 0) {
                                    logger.debug(tmp);
                                    array.add(tmp);
                                }
                            } catch (Exception e) {
                                logger.error("str : {}", str);
                                e.printStackTrace();
                                return null;
                            }
                        }
                    }
                }
            }
        }
        logger.debug("拆出{}个词", array.size());
        return array;
    }

    /**
     * 只切{wordLength}及以上的词
     *
     * @param str
     * @return
     */
    public static List<String> cutSentence(String str, int wordLength) {
        return cutSentence(str, wordLength, defaultMaxLength);
    }

    static String[] stopArray = {",", "，", ".", "。", "、", "/", ":", "：", "】", "【", ")", "(", "!", "！", ";", "；", "）", "（"};//切分句子内部使用

    public static void main(String[] args) {
        String str = "维护办公区环境域内的整洁及的会议室管理";
        List<String> cutSentence = cutSentence(str, 1);
        Collections.sort(cutSentence);
        for (String tmp : cutSentence) {
            System.out.println(tmp);
        }
    }
}
