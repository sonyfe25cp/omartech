package com.omartech.laibicanRobot.action;

import com.omartech.laibicanRobot.model.message.WeixinMessage;
import com.omartech.laibicanRobot.model.message.WeixinTextMessage;
import com.omartech.laibicanRobot.model.reply.ReplyMessage;
import com.omartech.laibicanRobot.utils.WeixinMessageConvertUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.Arrays;

/**
 * Created by omar on 14/11/11.
 */

@RequestMapping("/wx")
@Controller
public class WxAction {

    static Logger logger = LoggerFactory.getLogger(WxAction.class);


    final static String token = "omartech";

    @RequestMapping("/verify")
    @ResponseBody
    public String verifyWx(@RequestParam String signature, @RequestParam String timestamp, @RequestParam String nonce, @RequestParam String echostr) {
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


    @RequestMapping("/receive")
    @ResponseBody
    public String reveiveMsg(@RequestParam String signature,
                             @RequestParam String timestamp,
                             @RequestParam String nonce,
                             @RequestParam(required = true) String body) {

        if (!verifyRight(signature, timestamp, nonce, token)) {
            return "";
        }

        WeixinMessage message = WeixinMessageConvertUtil.convert2WeixinMessage(body);

        if(message == null){
            return "";
        }

        ReplyMessage replyMessage = getReplyMessage(message);
        if(replyMessage != null){
            String xml = replyMessage.toXML();
            logger.info("replayMessage : {}", xml);
            return xml;
        }
        return "";
    }

    private ReplyMessage getReplyMessage(WeixinMessage message) {


        String type = message.getType();

        ReplyMessage replyMessage = null;
        switch (type){
            case WeixinMessage.MESSAGE_TYPE_TEXT:
                WeixinTextMessage textMessage = (WeixinTextMessage)message;
                replayTextMessage(textMessage);
                break;
            default:
                break;
        }

        return replyMessage;
    }

    private void replayTextMessage(WeixinTextMessage textMessage) {
        String content = textMessage.getContent();

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


    static String compute(String timestamp, String nonce) {
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

    public static void main(String[] args) {
        String timestamp = "1415722779";
        String nonce = "1968723484";

        String compute = compute(timestamp, nonce);

        String res = "d93d5f45bd1b6e55c62ec621df1b2f7fdab98641";
        if (compute.equals(res)) {
            logger.info("true");
        } else {
            logger.info("false");
        }

    }


}
