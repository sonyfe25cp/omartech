package com.omartech.laibicanRobot.action;

import com.omartech.engine.client.ClientException;
import com.omartech.laibicanRobot.model.AppEnum;
import com.omartech.laibicanRobot.model.message.WeixinMessage;
import com.omartech.laibicanRobot.model.message.WeixinSubscribeMessage;
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

    static CenterService centerService = new CenterService();

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
//                    logger.info("replayMessage : {}", xml);
                    logger.info("xml returned");
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
//        logger.info("list : {}", str);

        String echo = new SHA1().getDigestOfString(str.getBytes()).toLowerCase();//SHA1加密
//        logger.info("echo : {}", echo);
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
            case WeixinMessage.MESSAGE_TYPE_EVENT://新关注
                WeixinSubscribeMessage subscribeMessage = (WeixinSubscribeMessage) message;
                String event = subscribeMessage.getEvent();
                switch (event) {
                    case WeixinMessage.MESSAGE_EVENT_SUBSCRIBE:
                        logger.info("new fans");
                        replyMessage = fetchSubscribeMsg(subscribeMessage);
                        break;
                    case WeixinMessage.MESSAGE_EVENT_UNSUBSCRIBE:
                        logger.info("a fans left");
                        replyMessage = fetchUnscribeMsg(message);
                        break;
                    default:
                        logger.info("what's event? {}", event);
                        break;
                }
                break;
            default:
                logger.info("msg type {} is not handled", type);
                break;
        }
        logger.info("return reply");
        return replyMessage;
    }

    ReplyMessage fetchSubscribeMsg(WeixinSubscribeMessage message) {
        String url = "http://mp.weixin.qq.com/s?__biz=MzAxNTA5MTc1NQ==&mid=202676955&idx=1&sn=f3d509a5b37801104a48d4c9ad7b8cdd#rd";//新年抽签
        String pic = "http://mmbiz.qpic.cn/mmbiz/Q2BricGyedbg9ziajFTlJoJ2PIlFQqAbsOibvGvkQJbeKLRaU6LvyeH5pOS4VsY1lrNACibjuV3cYTTqIwRFOkB0oA/0?tp=webp";
        ArticleReplyItem articleReplyItem = new ArticleReplyItem();
        articleReplyItem.setUrl(url);
        articleReplyItem.setPicUrl(pic);
        articleReplyItem.setTitle("为2015抽一发幸运签吧！");
        articleReplyItem.setDescription("2015羊年到，抽一卦幸运签，好运先知道，让朋友也来卜一下吧。");

        ArticleReply articleReply = new ArticleReply();
        articleReply.addArticleReplyItem(articleReplyItem);

        switch (message.getAppEnum()) {
            case Bican:
                ArticleReplyItem bicanIntro = new ArticleReplyItem();
                bicanIntro.setTitle("比惨是个什么公众号？");
                bicanIntro.setUrl("http://mp.weixin.qq.com/s?__biz=MzAxNTA5MTc1NQ==&mid=201453464&idx=2&sn=efa7f728eacde03f0dce2e63e601a215#rd");
                bicanIntro.setPicUrl("http://mmbiz.qpic.cn/mmbiz/Q2BricGyedbgv9RJ2TDR9UDfajtZlATUpy2QS10BXzW4lHaiaJtHXicT53LAho2NKTVysaNyjpGO0as1RRtwQuhlA/640?tp=webp");
                bicanIntro.setDescription("比惨是国内首家交流各种悲剧的平台~");
                articleReply.addArticleReplyItem(bicanIntro);
                break;
            case Hero:
                logger.info("sorry for hero");
                ArticleReplyItem tuijian = new ArticleReplyItem();
                tuijian.setTitle("抱歉，小编还在开发，推荐您个好玩的消消气");
                tuijian.setUrl("http://mp.weixin.qq.com/s?__biz=MzAxNTA5MTc1NQ==&mid=201453464&idx=2&sn=efa7f728eacde03f0dce2e63e601a215#rd");
                tuijian.setPicUrl("http://mmbiz.qpic.cn/mmbiz/Q2BricGyedbgv9RJ2TDR9UDfajtZlATUpy2QS10BXzW4lHaiaJtHXicT53LAho2NKTVysaNyjpGO0as1RRtwQuhlA/640?tp=webp");
                tuijian.setDescription("比惨是国内首家交流各种悲剧的平台~");
                articleReply.addArticleReplyItem(tuijian);
                break;
            case Jobs:
                logger.info("sorry for jobs");
                ArticleReplyItem tuijian2 = new ArticleReplyItem();
                tuijian2.setTitle("抱歉，小编还在开发，推荐您个好玩的消消气");
                tuijian2.setUrl("http://mp.weixin.qq.com/s?__biz=MzAxNTA5MTc1NQ==&mid=201453464&idx=2&sn=efa7f728eacde03f0dce2e63e601a215#rd");
                tuijian2.setPicUrl("http://mmbiz.qpic.cn/mmbiz/Q2BricGyedbgv9RJ2TDR9UDfajtZlATUpy2QS10BXzW4lHaiaJtHXicT53LAho2NKTVysaNyjpGO0as1RRtwQuhlA/640?tp=webp");
                tuijian2.setDescription("比惨是国内首家交流各种悲剧的平台~");
                articleReply.addArticleReplyItem(tuijian2);
                break;
            default:
                logger.info("what's app ? {}");
                break;
        }
        articleReply.setAddTime(new Date());
        articleReply.setFromName(message.getToName());
        articleReply.setToName(message.getFromName());
        return articleReply;
    }

    ReplyMessage fetchUnscribeMsg(WeixinMessage message) {
        NormalReply reply = new NormalReply();
        reply.setContent("少年，不爱我了么？");
        reply.setFromName(message.getToName());
        reply.setToName(message.getFromName());
        reply.setAddTime(new Date());
        return reply;
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
                ReplyMessage answer = centerService.findAnswer(textMessage);
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
