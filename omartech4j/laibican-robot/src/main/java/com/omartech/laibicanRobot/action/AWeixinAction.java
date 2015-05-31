package com.omartech.laibicanRobot.action;

import com.omartech.engine.client.ClientException;
import com.omartech.laibicanRobot.model.AppEnum;
import com.omartech.laibicanRobot.model.message.WeixinEventMessage;
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
import java.util.Arrays;
import java.util.Date;

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
                    replyMessage.setFromName(message.getToName());
                    replyMessage.setToName(message.getFromName());
                    replyMessage.setAddTime(new Date());
                    String xml = replyMessage.toXML().trim();
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
                if (replyMessage == null) {
                    logger.info("reply with default");
                    //replyMessage = CenterService.fetchBakUpMsg("嗯，然后呢？");
                }
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
            case WeixinMessage.MESSAGE_EVENT_CLICK://菜单点击
                logger.info("menu click");
                WeixinEventMessage eventMessage = (WeixinEventMessage) message;
                String eventKey = eventMessage.getEventKey();
                logger.info("eventKey: {}", eventKey);
                replyMessage = centerService.findResponseWith(eventMessage);
                break;
            case WeixinMessage.MESSAGE_EVENT_VIEW://菜单视图
                logger.info("menu view");
                break;
            case WeixinMessage.MESSAGE_TYPE_IMAGE:
                logger.info("有妹子发照片来了~~");
                replyMessage = CenterService.fetchBakUpMsg("神龙开始召唤小编.....若小编召唤失败，请再发一张增加成功几率~");
                break;
            case WeixinMessage.MESSAGE_TYPE_VIDEO:
                replyMessage = CenterService.fetchBakUpMsg("哇，视频~~赞！可机器人看不懂，不如发个照片来召唤小编大人吧~");
                break;
            case WeixinMessage.MESSAGE_TYPE_VOICE:
                replyMessage = CenterService.fetchBakUpMsg("唔...听不懂..要不要发个照片来召唤小编呢？");
                break;
            default:
                logger.info("msg type {} is not handled", type);
                break;
        }
        logger.info("return reply");
        return replyMessage;
    }


    static final String bicanIntroUrl = "http://mp.weixin.qq.com/s?__biz=MzAxNTA5MTc1NQ==&mid=201451846&idx=1&sn=d7e0f0492352ce934bdfbc49bea6e809#rd";

    ReplyMessage fetchSubscribeMsg(WeixinSubscribeMessage message) {
        String url = "http://mp.weixin.qq.com/s?__biz=MzAxNTA5MTc1NQ==&mid=202676955&idx=1&sn=f3d509a5b37801104a48d4c9ad7b8cdd#rd";//新年抽签
//        String url = "www.laibican.com/sactivity/yaoqian.html";//新年抽签
        String pic = "http://mmbiz.qpic.cn/mmbiz/Q2BricGyedbg9ziajFTlJoJ2PIlFQqAbsOibvGvkQJbeKLRaU6LvyeH5pOS4VsY1lrNACibjuV3cYTTqIwRFOkB0oA/0?tp=webp";
        ArticleReplyItem newyear = new ArticleReplyItem();
        newyear.setUrl(url);
        newyear.setPicUrl(pic);
        newyear.setTitle("为2015抽一发幸运签吧！");
        newyear.setDescription("2015羊年到，抽一卦幸运签，好运先知道，让朋友也来卜一下吧。");

        ArticleReply articleReply = new ArticleReply();
//        articleReply.addArticleReplyItem(newyear);

        switch (message.getAppEnum()) {
            case Bican:
                ArticleReplyItem bicanIntro = new ArticleReplyItem();
                bicanIntro.setTitle("比惨是个什么公众号？");
                bicanIntro.setUrl(bicanIntroUrl);
                bicanIntro.setPicUrl("http://mmbiz.qpic.cn/mmbiz/Q2BricGyedbgv9RJ2TDR9UDfajtZlATUpy2QS10BXzW4lHaiaJtHXicT53LAho2NKTVysaNyjpGO0as1RRtwQuhlA/640?tp=webp");
                bicanIntro.setDescription("比惨是国内首家交流各种悲剧的平台~");
                articleReply.addArticleReplyItem(bicanIntro);

                ArticleReplyItem howtoplay = new ArticleReplyItem();
                howtoplay.setTitle("比惨公众号的玩法和说明");
                howtoplay.setPicUrl("https://mmbiz.qlogo.cn/mmbiz/Q2BricGyedbj1ooQVLX1FHu8REQgjj5H0tshtfg51rDZCibz82kPv6zFA0hKFZkS67w5x7Doeodg7hZBxcbEbbrA/0");
                howtoplay.setUrl("http://mp.weixin.qq.com/s?__biz=MzAxNTA5MTc1NQ==&mid=203856822&idx=1&sn=1bfee7b534503e4688be4969d3263e6f#rd");
                howtoplay.setDescription("这里告诉你怎么玩死小编~");
                articleReply.addArticleReplyItem(howtoplay);
                break;
            case Hero:
                logger.info("sorry for hero");
                ArticleReplyItem tuijian = new ArticleReplyItem();
                tuijian.setTitle("抱歉，小编还在开发，推荐您个好玩的消消气");
                tuijian.setUrl(bicanIntroUrl);
                tuijian.setPicUrl("http://mmbiz.qpic.cn/mmbiz/Q2BricGyedbgv9RJ2TDR9UDfajtZlATUpy2QS10BXzW4lHaiaJtHXicT53LAho2NKTVysaNyjpGO0as1RRtwQuhlA/640?tp=webp");
                tuijian.setDescription("比惨是国内首家交流各种悲剧的平台~");
                articleReply.addArticleReplyItem(tuijian);
                articleReply.addArticleReplyItem(newyear);
                break;
            case Jobs://招聘模块暂时不用api了
                logger.info("sorry for jobs");
//                ArticleReplyItem tuijian2 = new ArticleReplyItem();
//                tuijian2.setTitle("抱歉，小编还在开发，推荐您个好玩的消消气");
//                tuijian2.setUrl("http://mp.weixin.qq.com/s?__biz=MzAxNTA5MTc1NQ==&mid=201453464&idx=2&sn=efa7f728eacde03f0dce2e63e601a215#rd");
//                tuijian2.setPicUrl("http://mmbiz.qpic.cn/mmbiz/Q2BricGyedbgv9RJ2TDR9UDfajtZlATUpy2QS10BXzW4lHaiaJtHXicT53LAho2NKTVysaNyjpGO0as1RRtwQuhlA/640?tp=webp");
//                tuijian2.setDescription("比惨是国内首家交流各种悲剧的平台~");
//                articleReply.addArticleReplyItem(tuijian2);
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
        try {
            ReplyMessage replyMessage = customeredReplay(textMessage);
            if (replyMessage == null) {
                replyMessage = centerService.findAnswer(textMessage);
            }
            return replyMessage;
        } catch (SQLException e) {
            logger.error("center service error");
            e.printStackTrace();
            return null;
        } catch (ClientException e) {
            e.printStackTrace();
            return null;
        }
    }

    public abstract ReplyMessage customeredReplay(WeixinTextMessage textMessage);

}
