package com.omartech.laibicanRobot.action.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by omar on 15-1-5.
 */
@Controller
@RequestMapping("/admin/query")
public class QueryLogAction {

    @RequestMapping("/list")
    public ModelAndView listQuery(){

        return new ModelAndView("");
    }


}
