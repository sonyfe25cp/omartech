package com.omartech.weixin.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by OmarTech on 15-2-2.
 */
@Controller
public class CommonAction {

    @RequestMapping("")
    public String index(){
        return "/index";
    }

}
