package com.omartech.laibicanRobot.action.admin;

import com.omartech.laibicanRobot.model.reply.ReplyMessage;
import com.omartech.laibicanRobot.service.CenterService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;
import java.util.List;

/**
 * 提前设定好的回复内容
 * Created by omar on 14-12-18.
 */
@Controller
@RequestMapping("/replays")
public class ReplyAction {

    @RequestMapping("/list")
    public ModelAndView listReplays(
            @RequestParam(required = false, defaultValue = "0") int pageSize,
            @RequestParam(required = false, defaultValue = "20") int pageNo
    ) {
        int begin = pageNo * pageSize;
        int limit = pageSize;
        CenterService centerService = new CenterService();
        List<ReplyMessage> replyMessages = null;
        try {
            replyMessages = centerService.listReplyMessage(begin, limit);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ModelAndView("/admin/reply/replyList")
                .addObject("list", replyMessages)
                .addObject("pageNo", pageNo)
                .addObject("pageSize", pageSize);
    }

    @RequestMapping("/add")
    public String addGet() {
        return "";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addPost(
            @RequestParam String replyEnum,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String url,
            @RequestParam(required = false) String picUrl,
            @RequestParam(required = false) String content,
            @RequestParam(required = false) String array
    ) {
        switch (replyEnum) {
            case "Text":
                break;
            case "List":
                break;
            case "Article":
                break;


        }


        return null;
    }


}
