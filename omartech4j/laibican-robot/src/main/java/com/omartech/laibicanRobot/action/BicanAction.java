package com.omartech.laibicanRobot.action;

import com.omartech.laibicanRobot.model.message.WeixinTextMessage;
import com.omartech.laibicanRobot.model.reply.ReplyMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by omar on 14/11/11.
 */

@RequestMapping("/bican")
@Controller
public class BicanAction extends AWeixinAction {

    static Logger logger = LoggerFactory.getLogger(BicanAction.class);


    final static String BICANACCOUNT = "";


    @RequestMapping("/verify")
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


    @RequestMapping("/receive")
    @ResponseBody
    public String reveiveMsg(@RequestParam String signature,
                             @RequestParam String timestamp,
                             @RequestParam String nonce,
                             @RequestParam(required = true) String body) {
        return super.reveiveMsg(signature, timestamp, nonce, body);
    }

    @Override
    protected ReplyMessage replayTextMessage(WeixinTextMessage textMessage) {
        String content = textMessage.getContent();


        return null;
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
