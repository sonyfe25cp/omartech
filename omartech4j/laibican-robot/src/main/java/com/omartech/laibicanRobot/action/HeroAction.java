package com.omartech.laibicanRobot.action;

import com.omartech.laibicanRobot.model.message.WeixinTextMessage;
import com.omartech.laibicanRobot.model.reply.ArticleReply;
import com.omartech.laibicanRobot.model.reply.ArticleReplyItem;
import com.omartech.laibicanRobot.model.reply.NormalReply;
import com.omartech.laibicanRobot.model.reply.ReplyMessage;
import com.omartech.laibicanRobot.service.CenterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by omar on 14-12-17.
 */
@Controller
@RequestMapping("/hero")
public class HeroAction extends AWeixinAction {

    static Logger logger = LoggerFactory.getLogger(HeroAction.class);


    final static String token = "omartech";


    @RequestMapping(value = "/receive", method = RequestMethod.GET)
    @ResponseBody
    public String verifyWx(@RequestParam String signature,
                           @RequestParam String timestamp,
                           @RequestParam String nonce,
                           @RequestParam String echostr) {
        logger.info("signature, timestamp, nonce, echostr: {}, {}, {}, {}", new String[]{signature, timestamp, nonce, echostr});
        String echo = compute(timestamp, nonce);
        if (echo.equals(signature)) {
            logger.info("verify success");
            return echostr;
        } else {
            logger.info("verify error");
            return null;
        }
    }


    @RequestMapping(value = "/receive", method = RequestMethod.POST)
    public void reveiveMsg(@RequestParam String signature,
                           @RequestParam String timestamp,
                           @RequestParam String nonce,
                           @RequestBody String body,
                           HttpServletResponse response
    ) {
        logger.info("i have receive the post req");
        String xml = super.reveiveMsg(signature, timestamp, nonce, body);
        response.setCharacterEncoding("UTF-8");
        try {
            PrintWriter writer = response.getWriter();
            writer.write(xml);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/receive1", method = RequestMethod.GET)
    @ResponseBody
    public String testxml() {
        logger.info("i have receive the post req");
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<xml><FromUserName><![CDATA[chenjie_bit]]></FromUserName><ToUserName><![CDATA[oQ9gFjwV__qHiWucNUE0ag4o4aZI]]></ToUserName><MsgType><![CDATA[text]]></MsgType><CreateTime>1418806516</CreateTime><Content><![CDATA[world hello微信开发也蛮累的。。别瞎加需求了]]></Content></xml>";
        logger.info("xml:{}", xml);
        return xml;
    }

    //    @ResponseBody
    @RequestMapping(value = "/receive2", method = RequestMethod.GET)
    public void testxml1(HttpServletResponse response) {
        logger.info("i have receive the post req");
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<xml><FromUserName><![CDATA[chenjie_bit]]></FromUserName><ToUserName>" +
                "<![CDATA[oQ9gFjwV__qHiWucNUE0ag4o4aZI]]></ToUserName>" +
                "<MsgType><![CDATA[text]]></MsgType>" +
                "<CreateTime>1418806516</CreateTime>" +
                "<Content><![CDATA[world hello，微信开发也蛮累的。。别瞎加需求了]]></Content></xml>";
        logger.info("xml:{}", xml);

        response.setContentType("text/xml");
        response.setCharacterEncoding("UTF-8");
        try {
            PrintWriter writer = response.getWriter();
            writer.write(xml);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        return xml;
    }

    @RequestMapping(value = "/receive3", method = RequestMethod.GET)
    public void testxml3(HttpServletResponse response) {
        logger.info("i have receive the post req");
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<xml><FromUserName><![CDATA[chenjie_bit]]></FromUserName>" +
                "<ToUserName><![CDATA[oQ9gFjwV__qHiWucNUE0ag4o4aZI]]>" +
                "</ToUserName><MsgType><![CDATA[text]]></MsgType>" +
                "<CreateTime>1418806516</CreateTime>" +
                "<Content><![CDATA[world hello微信开发也蛮累的。。别瞎加需求了]]></Content></xml>";
        logger.info("xml:{}", xml);

        response.setContentType("application/xml");
        response.setCharacterEncoding("UTF-8");
        try {
            PrintWriter writer = response.getWriter();
            writer.write(xml);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        return xml;
    }


    @Override
    protected ReplyMessage replayTextMessage(WeixinTextMessage textMessage) {
        System.out.println(textMessage);
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
            CenterService c = new CenterService();
            try {
                ReplyMessage answer = c.findAnswer(content);
                answer.setFromName(toName);
                answer.setToName(fromName);
                answer.setAddTime(new Date());
                return answer;
            } catch (SQLException e) {
                logger.error("center service error");
                e.printStackTrace();
                return null;
            }
        }

    }

}
