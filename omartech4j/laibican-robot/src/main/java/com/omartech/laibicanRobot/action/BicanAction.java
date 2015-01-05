package com.omartech.laibicanRobot.action;

import com.omartech.laibicanRobot.model.AppEnum;
import com.omartech.laibicanRobot.model.message.WeixinTextMessage;
import com.omartech.laibicanRobot.model.reply.ReplyMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by omar on 14/11/11.
 */

@RequestMapping("/bican")
@Controller
public class BicanAction extends AWeixinAction {

    static Logger logger = LoggerFactory.getLogger(BicanAction.class);


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
        String xml = super.reveiveMsg(signature, timestamp, nonce, body, AppEnum.Bican);
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

}
