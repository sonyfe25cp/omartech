package com.omartech.laibicanRobot.action;

import com.omartech.data.gen.Job;
import com.omartech.data.gen.JobResponse;
import com.omartech.laibicanRobot.model.AppEnum;
import com.omartech.laibicanRobot.model.message.WeixinTextMessage;
import com.omartech.laibicanRobot.model.reply.ArticleReply;
import com.omartech.laibicanRobot.model.reply.ArticleReplyItem;
import com.omartech.laibicanRobot.model.reply.NormalReply;
import com.omartech.laibicanRobot.model.reply.ReplyMessage;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

/**
 * Created by omar on 14-12-17.
 */
@Controller
@RequestMapping("/jobs")
public class JobsAction extends AWeixinAction {
    static Logger logger = LoggerFactory.getLogger(JobsAction.class);

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
        String xml = super.reveiveMsg(signature, timestamp, nonce, body, AppEnum.Jobs);
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
        if (content.equals("today")) {
            String today = DateFormatUtils.format(new Date(), "yyyy-MM-dd");
            JobResponse jobsToday = centerService.findJobsToday(today, false, "", 0, 30);
            if (jobsToday != null) {
                ArticleReply replyMessage = new ArticleReply();
                ArticleReplyItem articleReplyItem = new ArticleReplyItem();
                articleReplyItem.setTitle(today + "招聘数为：" + jobsToday.getTotal());
                articleReplyItem.setUrl("http://jobs.omartech.com/jobs/today");
                articleReplyItem.setPicUrl(centerService.findAPicture());
                articleReplyItem.setDescription("");
                replyMessage.addArticleReplyItem(articleReplyItem);
                return replyMessage;
            } else {
                NormalReply normalReply = new NormalReply();
                normalReply.setContent("今日招聘还没出来，过一个小时再来看吧。");
                return normalReply;
            }
        }
        return null;
    }

    @RequestMapping("/today")
    public ModelAndView todayJobs(
            @RequestParam(required = false, defaultValue = "1") int pageNo,
            @RequestParam(required = false, defaultValue = "") String area,
            @RequestParam(required = false, defaultValue = "false") boolean intern
    ) {
        int limit = 30;
        int offset = (pageNo - 1) * limit;
        String today = DateFormatUtils.format(new Date(), "yyyy-MM-dd");
        JobResponse jobsToday = centerService.findJobsToday(today, intern, area, offset, limit);
        ModelAndView modelAndView = new ModelAndView("/jobs/list");
        if (jobsToday != null && jobsToday.getJobs() != null) {
            List<Job> jobs = jobsToday.getJobs();
            modelAndView.addObject("jobs", jobs);
        }
        modelAndView.addObject("area", area);
        modelAndView.addObject("pageNo", pageNo);
        modelAndView.addObject("pageSize", limit);
        modelAndView.addObject("today", today);
        modelAndView.addObject("intern", intern);
        return modelAndView;
    }
}
