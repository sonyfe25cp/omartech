package com.omartech.laibicanRobot.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by omar on 14/11/12.
 */

@Controller
public class RouteAction {
    static Logger logger = LoggerFactory.getLogger(RouteAction.class);

    @RequestMapping("/index")
    public String index() {

        logger.info("index");
        return "/index";
    }

    @RequestMapping("")
    public String defaultPage() {
        logger.info("default index");
        return "/index";
    }
}
