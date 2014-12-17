package com.omartech.laibicanRobot.action;

import com.omartech.laibicanRobot.model.message.WeixinTextMessage;
import com.omartech.laibicanRobot.model.reply.ArticleReply;
import com.omartech.laibicanRobot.model.reply.ReplyMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * Created by omar on 14-12-17.
 */
@Controller
@RequestMapping("/hero")
public class HeroAction extends WeixinAction {

    static Logger logger = LoggerFactory.getLogger(BicanAction.class);


    final static String token = "omartech";

    final static String HEROACCOUNT = "chenjie_bit";


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
    @ResponseBody
    public String reveiveMsg(@RequestParam String signature,
                             @RequestParam String timestamp,
                             @RequestParam String nonce,
                             @RequestParam(required = true) String body) {
        return super.reveiveMsg(signature, timestamp, nonce, body);
    }

    @Override
    protected ReplyMessage replayTextMessage(WeixinTextMessage textMessage) {
        System.out.println(textMessage);
        String content = textMessage.getContent();
        String fromName = textMessage.getFromName();
        String toName = textMessage.getToName();
        int date10 = textMessage.getDate10();
        long messageId = textMessage.getMessageId();

        logger.info("from:{} to:{} at:{} \ncontent:{}, id:{}", new String[]{fromName, toName, date10 + "", content, messageId + ""});

        ReplyMessage replyMessage = new ArticleReply();
        replyMessage.setContent("hello world");
        replyMessage.setAddTime(new Date());
        replyMessage.setFromName(HEROACCOUNT);
        replyMessage.setToName(fromName);

        return replyMessage;
    }

}
