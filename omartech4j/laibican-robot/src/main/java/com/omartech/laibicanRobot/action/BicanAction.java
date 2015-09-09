package com.omartech.laibicanRobot.action;

import com.omartech.laibicanRobot.model.AppEnum;
import com.omartech.laibicanRobot.model.message.WeixinTextMessage;
import com.omartech.laibicanRobot.model.reply.ArticleReply;
import com.omartech.laibicanRobot.model.reply.ArticleReplyItem;
import com.omartech.laibicanRobot.model.reply.ReplyMessage;
import com.omartech.laibicanRobot.service.CenterService;
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

    @Override
    public ReplyMessage customeredReplay(WeixinTextMessage textMessage) {
        String content = textMessage.getContent();
        if (content.equals("你妹")) {
            return CenterService.fetchBakUpMsg("你妹");
        } else if (content.equals("征友39")) {
            return CenterService.fetchBakUpMsg("手机号：13975550774，请自行联系，本公众号免费帮忙征友。");
        } else if (content.startsWith("征友")) {
            return CenterService.fetchBakUpMsg("征友的话，请发照片，联系方式，城市，年龄给我哦~");
        } else if (content.startsWith("【比惨】") || content.startsWith("[比惨]") || content.startsWith("比惨")) {
            return CenterService.fetchBakUpMsg("少侠不哭，虽然很惨，但还有人更惨，我先帮你记下来了。");
        } else if (content.startsWith("【树洞】") || content.startsWith("[树洞]")) {
            return CenterService.fetchBakUpMsg("秘密说出来就好了，我会认真听的。");
        } else if (content.startsWith("在吗") || content.equals("你好") || content.equals("小编好")) {
            return CenterService.fetchBakUpMsg("hi，请讲故事吧~");

        } else if (content.contains("油炸") || content.contains("最惨")) {
            ArticleReply articleReply = new ArticleReply();
            ArticleReplyItem articleReplyItem = new ArticleReplyItem();
            articleReplyItem.setPicUrl("http://mmbiz.qpic.cn/mmbiz/Q2BricGyedbg1y3lAWQecOX7VIHHDibrYGw3WjBdAxHrtY0mlS2vbGcLScn17gHg4anVib8ronJCrZ27BwK8gy8WQ/640?tp=webp&wxfrom=5");
            articleReplyItem.setUrl("http://mp.weixin.qq.com/s?__biz=MzAxNTA5MTc1NQ==&mid=203804075&idx=1&sn=7948368c0c98e799e8dd896f8feaf2c6#rd");
            articleReplyItem.setTitle("【我最惨】手被油炸了");
            articleReplyItem.setDescription("老婆去熬油然后出去卖东西了，我感觉不对劲去看，油着火了，把旁边的东西都烧着了，我一着急就上去一把抓住锅想把它扔出去，可是太烫手一抖油就搞到手上。手被油炸了/::'(");
            articleReply.addArticleReplyItem(articleReplyItem);
            return articleReply;
        } else if (content.equals("比惨")) {
            return CenterService.fetchBakUpMsg("讲个你自己的故事呗？各种不如意的事情都可以讲，咱这都是匿名的。一张自拍照片就可以召唤小编出来聊天了~~~");
        } else if (content.equals("树洞")) {
            return CenterService.fetchBakUpMsg("坐在小板凳准备听你讲故事了。各种不如意的事情都可以讲，咱这都是匿名的。一张自拍照片可以召唤小编出来聊天了~~~");
        } else if (content.contains("小编")) {
            return CenterService.fetchBakUpMsg("一张自拍照片可以召唤小编出来聊天了~~~小编最喜欢看妹子照片了~都会保密不给其他人看的");
        } else if (content.equals("晚安") || content.equals("再见") || content.equals("拜拜") || content.equals("你大爷") || content.equals("尼玛") || content.equals("傻比")) {
            return CenterService.fetchBakUpMsg(content);
        } else if (content.contains("共") && content.contains("产") && content.contains("党") && content.contains("没有") && content.contains("腐败")) {
            return CenterService.fetchBakUpMsg("去你妈逼的，该滚哪玩就滚哪玩去");
        } else if (content.equals("出轨")) {
            ArticleReply articleReply = new ArticleReply();
            ArticleReplyItem articleReplyItem = new ArticleReplyItem();
            articleReplyItem.setPicUrl("http://mmbiz.qpic.cn/mmbiz/Q2BricGyedbiajT9SEMfSKhmXe3QaYzRtxLfn97gImS5jwVUDA3JziaShleAFh9ZVV08IoFqVPoCDd7Nn3ZZK8sMA/640?tp=webp&wxfrom=5");
            articleReplyItem.setUrl("http://mp.weixin.qq.com/s?__biz=MzAxNTA5MTc1NQ==&mid=202020839&idx=1&sn=5cfcdfe65729ff18c6254aee6a59d047#rd");
            articleReplyItem.setTitle("【答案揭晓】你敢挑战吗？！到底是几楼的女人出轨？");
            articleReplyItem.setDescription("到底是几楼的女人出轨了呢？你猜到了么？");
            articleReply.addArticleReplyItem(articleReplyItem);
            return articleReply;
        }else if(content.contains("睡不着")){
            return CenterService.fetchBakUpMsg("拿给我讲个你的故事吧~");
        } else if (content.length() > 10) {
            return CenterService.fetchBakUpMsg("还有么？请继续");
        } else {
            return null;
        }
    }
}
