package com.omartech.laibicanRobot.action;

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
        String[] list = new String[]{token, timestamp, nonce};
        Arrays.sort(list);
        String str = "";
        for (int i = 0; i < list.length; i++) {
            str += list[i];
        }
        String echo = new SHA1().getDigestOfString(str.getBytes());//SHA1加密

        if (echo.equals(signature)) {
            logger.info("verify success");
            return echostr;
        } else {
            return null;
        }
    }


}
