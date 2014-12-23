package com.omartech.laibicanRobot.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by omar on 14/11/12.
 */

@Controller
public class RouteAction {
    static Logger logger = LoggerFactory.getLogger(RouteAction.class);

    @RequestMapping("/index")
    public String index() {
        return "/index";
    }

    @RequestMapping("")
    public String defaultPage() {
        return "/index";
    }

    @RequestMapping("/bican")
    public String laibican() {
        return "/homepages/bican";
    }

    @RequestMapping("/heros")
    public String heros() {
        return "/homepages/heros";
    }

    @RequestMapping("/jobs")
    public String jobs() {
        return "/homepages/jobs";
    }

    @RequestMapping(value = "/testt", method = RequestMethod.GET)
    public String test() {
        return "/test";
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public ModelAndView test(@RequestParam String a) {
        logger.info("测试，哈哈:{}", a);
        return new ModelAndView("/res").addObject("b", a);
    }

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public ModelAndView test2(@RequestParam String a) {
        logger.info("测试，哈哈2:{}", a);
        return new ModelAndView("/res").addObject("b", a);
    }

}
