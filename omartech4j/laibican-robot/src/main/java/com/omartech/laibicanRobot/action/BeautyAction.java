package com.omartech.laibicanRobot.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by OmarTech on 15-2-13.
 */

@Controller
public class BeautyAction {
    @RequestMapping("/beauty/{id}")
    public ModelAndView show(@PathVariable String id){
        return null;
    }
}
