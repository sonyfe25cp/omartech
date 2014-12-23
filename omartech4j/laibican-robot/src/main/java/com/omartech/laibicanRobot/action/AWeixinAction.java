package com.omartech.laibicanRobot.action;

import com.omartech.laibicanRobot.model.message.WeixinMessage;
import com.omartech.laibicanRobot.model.message.WeixinTextMessage;
import com.omartech.laibicanRobot.model.reply.ReplyMessage;
import com.omartech.laibicanRobot.utils.WeixinMessageConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;

/**
 * Created by omar on 14-12-17.
 */
public abstract class AWeixinAction {

    static Logger logger = LoggerFactory.getLogger(AWeixinAction.class);

    protected final static String token = "omartech";


    public String reveiveMsg(String signature,
                             String timestamp,
                             String nonce,
                             String body) {

        if (!verifyRight(signature, timestamp, nonce, token)) {
            logger.error("verify error, return");
            return "";
        } else {
            WeixinMessage message = WeixinMessageConvertUtil.convert2WeixinMessage(body);
            if (message == null) {
                logger.error("can't receive the message, return");
                return "";
            } else {
                ReplyMessage replyMessage = getReplyMessage(message);
                logger.info("1");
                if (replyMessage != null) {
                    logger.info("here?");
                    String xml = replyMessage.toXML().trim();
//                    if (replyMessage instanceof ArticleReply) {
//                        logger.info("article");
//                        ArticleReply replyMessage1 = (ArticleReply) replyMessage;
//                        xml = replyMessage1.toXML();
//                    } else if (replyMessage instanceof NormalReply) {
//                        logger.info("normal");
//                        NormalReply replyMessage1 = (NormalReply) replyMessage;
//                        xml = replyMessage1.toXML();
//                    } else {
//                        logger.info("...");
//                    }
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

    protected abstract ReplyMessage replayTextMessage(WeixinTextMessage textMessage);

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

}
