package com.omartech.laibicanRobot.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.MessageDigest;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by omar on 14/11/11.
 */

@RequestMapping("/wx")
@Controller
public class WxAction {


    static Logger logger = LoggerFactory.getLogger(WxAction.class);

    @RequestMapping("/index")
    public String index() {
        return "/index";
    }

    @RequestMapping("")
    public String defaultPage() {
        return "/index";
    }

    final static String token = "omartech";

    @RequestMapping("/verify")
    public String verifyWx(@RequestParam String signature, @RequestParam String timestamp, @RequestParam String nonce, @RequestParam String echostr) {
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
