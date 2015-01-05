package com.omartech.laibicanRobot.action.admin;

import com.omartech.laibicanRobot.filter.Rule;
import com.omartech.laibicanRobot.service.CenterService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;
import java.util.List;

/**
 * 相关规则
 * Created by omar on 14-12-18.
 */
@Controller
@RequestMapping("/rules")
public class RuleAction {

    @RequestMapping("/list")
    public ModelAndView listRules(
            @RequestParam(defaultValue = "0", required = false) int pageNo,
            @RequestParam(defaultValue = "30", required = false) int pageSize
    ) {
        int start = pageNo * pageSize;
        CenterService centerService = new CenterService();
        List<Rule> rules = null;
        try {
            rules = centerService.fetchRules(start, pageSize);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ModelAndView("").addObject("rules", rules).addObject("pageNo", pageNo);
    }

}
