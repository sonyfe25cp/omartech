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

    @Override
    public ReplyMessage customeredReplay(WeixinTextMessage textMessage) {
        return null;
    }

//    @RequestMapping(value = "/receive1", method = RequestMethod.GET)
//    @ResponseBody
//    public String testxml() {
//        logger.info("i have receive the post req");
//        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
//                "<xml><FromUserName><![CDATA[chenjie_bit]]></FromUserName><ToUserName><![CDATA[oQ9gFjwV__qHiWucNUE0ag4o4aZI]]></ToUserName><MsgType><![CDATA[text]]></MsgType><CreateTime>1418806516</CreateTime><Content><![CDATA[world hello微信开发也蛮累的。。别瞎加需求了]]></Content></xml>";
//        logger.info("xml:{}", xml);
//        return xml;
//    }
//
//    //    @ResponseBody
//    @RequestMapping(value = "/receive2", method = RequestMethod.GET)
//    public void testxml1(HttpServletResponse response) {
//        logger.info("i have receive the post req");
//        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
//                "<xml><FromUserName><![CDATA[chenjie_bit]]></FromUserName><ToUserName>" +
//                "<![CDATA[oQ9gFjwV__qHiWucNUE0ag4o4aZI]]></ToUserName>" +
//                "<MsgType><![CDATA[text]]></MsgType>" +
//                "<CreateTime>1418806516</CreateTime>" +
//                "<Content><![CDATA[world hello，微信开发也蛮累的。。别瞎加需求了]]></Content></xml>";
//        logger.info("xml:{}", xml);
//
//        response.setContentType("text/xml");
//        response.setCharacterEncoding("UTF-8");
//        try {
//            PrintWriter writer = response.getWriter();
//            writer.write(xml);
//            writer.flush();
//            writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
////        return xml;
//    }
//
//    @RequestMapping(value = "/receive3", method = RequestMethod.GET)
//    public void testxml3(HttpServletResponse response) {
//        logger.info("i have receive the post req");
//        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
//                "<xml><FromUserName><![CDATA[chenjie_bit]]></FromUserName>" +
//                "<ToUserName><![CDATA[oQ9gFjwV__qHiWucNUE0ag4o4aZI]]>" +
//                "</ToUserName><MsgType><![CDATA[text]]></MsgType>" +
//                "<CreateTime>1418806516</CreateTime>" +
//                "<Content><![CDATA[world hello微信开发也蛮累的。。别瞎加需求了]]></Content></xml>";
//        logger.info("xml:{}", xml);
//
//        response.setContentType("application/xml");
//        response.setCharacterEncoding("UTF-8");
//        try {
//            PrintWriter writer = response.getWriter();
//            writer.write(xml);
//            writer.flush();
//            writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
////        return xml;
//    }


}
