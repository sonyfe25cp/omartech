package com.omartech.laibicanRobot.action;

import com.omartech.engine.client.ClientException;
import com.omartech.laibicanRobot.model.AppEnum;
import com.omartech.laibicanRobot.model.message.WeixinMessage;
import com.omartech.laibicanRobot.model.message.WeixinTextMessage;
import com.omartech.laibicanRobot.model.reply.ArticleReply;
import com.omartech.laibicanRobot.model.reply.ArticleReplyItem;
import com.omartech.laibicanRobot.model.reply.NormalReply;
import com.omartech.laibicanRobot.model.reply.ReplyMessage;
import com.omartech.laibicanRobot.service.CenterService;
import com.omartech.laibicanRobot.utils.WeixinMessageConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by omar on 14-12-17.
 */
public abstract class AWeixinAction {

    static Logger logger = LoggerFactory.getLogger(AWeixinAction.class);

    protected final static String token = "omartech";

    public String reveiveMsg(String signature,
                             String timestamp,
                             String nonce,
                             String body,
                             AppEnum appEnum) {

        if (!verifyRight(signature, timestamp, nonce, token)) {
            logger.error("verify error, return");
            return "";
        } else {
            WeixinMessage message = WeixinMessageConvertUtil.convert2WeixinMessage(body);
            message.setAppEnum(appEnum);
            if (message == null) {
                logger.error("can't receive the message, return");
                return "";
            } else {
                ReplyMessage replyMessage = getReplyMessage(message);
                if (replyMessage != null) {
                    String xml = replyMessage.toXML().trim();
                    logger.info("replayMessage : {}", xml);
                    return xml;
                } else {
                    logger.error("replay is null");
                    return "";
                }
            }
        }
    }


    protected static String compute(String timestamp, String nonce) {
        String[] list = new String[]{token, timestamp, nonce};
        Arrays.sort(list);
        String str = "";
        for (int i = 0; i < list.length; i++) {
            str += list[i];
        }
        logger.info("list : {}", str);

        String echo = new SHA1().getDigestOfString(str.getBytes()).toLowerCase();//SHA1加密
        logger.info("echo : {}", echo);
        return echo;
    }

    private ReplyMessage getReplyMessage(WeixinMessage message) {

        String type = message.getType();

        ReplyMessage replyMessage = null;
        switch (type) {
            case WeixinMessage.MESSAGE_TYPE_TEXT:
                WeixinTextMessage textMessage = (WeixinTextMessage) message;
                replyMessage = replayTextMessage(textMessage);
                logger.info("find match");
                break;
            default:
                break;
        }
        logger.info("return reply");
        return replyMessage;
    }

    boolean verifyRight(@RequestParam String signature,
                        @RequestParam String timestamp,
                        @RequestParam String nonce,
                        String token) {

        String echo = compute(timestamp, nonce);

        if (echo.equals(signature)) {
            return true;
        } else {
            return false;
        }
    }

    protected ReplyMessage replayTextMessage(WeixinTextMessage textMessage) {
        logger.info(textMessage.toString());
        String content = textMessage.getContent();
        String fromName = textMessage.getFromName();
        String toName = textMessage.getToName();
        int date10 = textMessage.getDate10();
        long messageId = textMessage.getMessageId();

        logger.info("from:{} to:{} at:{} content:{}, id:{}", new String[]{fromName, toName, date10 + "", content, messageId + ""});
//        ReplyMessage replyMessage = null;
        if (content.equals("哈哈")) {
            ArticleReply replyMessage = new ArticleReply();
            ArticleReplyItem articleReplyItem = new ArticleReplyItem();
            articleReplyItem.setPicUrl("http://mmbiz.qpic.cn/mmbiz/dYyiavLsrKlsyp3jKP8iahWQWEtGjgvSsvEwOcaCTbawseAvVa6UQED0YTNXDwbSLiaVgibLGpjlCHeC07dzgm916g/640?tp=webp");
            articleReplyItem.setUrl("http://mp.weixin.qq.com/s?__biz=MjM5Mjk2Njg4Mw==&mid=201881251&idx=1&sn=080d67a33e3d3e0300e63256c2eb20d2#rd");
            articleReplyItem.setTitle("这是第一个");
            articleReplyItem.setDescription("wolegequ");
            ArticleReplyItem articleReplyItem2 = new ArticleReplyItem();
            articleReplyItem2.setPicUrl("http://mmbiz.qpic.cn/mmbiz/dYyiavLsrKlsyp3jKP8iahWQWEtGjgvSsvYn8pNibibHGSfCNJouchgNwicn45BZ1qcSaWhD7iaWRf95mRlicK0DWBDoQ/640?tp=webp");
            articleReplyItem2.setUrl("http://mp.weixin.qq.com/s?__biz=MjM5MTU2MDYwNw==&mid=201513154&idx=1&sn=70f658a1023b75eac9a91d489e7f7e5c#rd");
            articleReplyItem2.setTitle("这是第二个");
            articleReplyItem2.setDescription("hhahahahahahahah");
            List<ArticleReplyItem> articleReplyItemList = new ArrayList<>();
            articleReplyItemList.add(articleReplyItem);
            articleReplyItemList.add(articleReplyItem2);
            replyMessage.setArticleReplyItemList(articleReplyItemList);
            replyMessage.setAddTime(new Date());
            replyMessage.setFromName(toName);
            replyMessage.setToName(fromName);
            logger.info("replayTextMsg over");
            return replyMessage;
        } else if (content.equals("1")) {
            logger.info("this");
            NormalReply replyMessage = new NormalReply();
            replyMessage.setToName(fromName);
            replyMessage.setFromName(toName);
            replyMessage.setContent("world hello,写后台真的很不容易，别瞎鸡巴加需求了" + content);
            replyMessage.setAddTime(new Date());
            logger.info("over");
            return replyMessage;
        } else {
            try {
                CenterService c = new CenterService();
                ReplyMessage answer = c.findAnswer(textMessage);
                return answer;
            } catch (SQLException e) {
                logger.error("center service error");
                e.printStackTrace();
                return null;
            } catch (ClientException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
