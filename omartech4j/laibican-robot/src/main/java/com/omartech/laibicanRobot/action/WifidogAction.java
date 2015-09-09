package com.omartech.laibicanRobot.action;

import com.omartech.laibicanRobot.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

/**
 * Created by OmarTech on 15-7-11.
 */

@Controller
@RequestMapping("/auth")
public class WifidogAction {

    static Logger logger = LoggerFactory.getLogger(WifidogAction.class);

    @RequestMapping("/ping")
    public void ping(@RequestParam(required = false) String gw_id,
                     @RequestParam(required = false) String sys_uptime,
                     @RequestParam(required = false) String sys_memfree,
                     @RequestParam(required = false) String sys_load,
                     @RequestParam(required = false) String wifidog_uptime,
                     HttpServletResponse response,
                     HttpServletRequest request) {

        logger.info("ping -- gw_id:{}, sys_uptime:{}", gw_id, sys_uptime);
        logger.info("ping -- sys_memfree:{}, sys_load:{}, wifidog_uptime:{}", new String[]{sys_memfree, sys_load, wifidog_uptime});


        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            Object o = headerNames.nextElement();
        }

        PrintWriter writer = null;
        try {
            writer = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        writer.write("Pong");
        writer.close();
    }

    //    gw_address=192.168.1.1&
// gw_port=2060
// &gw_id=24660045
// &ip=192.168.1.151
// &mac=6c:40:08:ad:78:98
// &url=http%3A%2F%2Fwww.bit.edu.cn%2F
    @RequestMapping("/login")
    public ModelAndView login(
            @RequestParam(required = false) String gw_address,
            @RequestParam(required = false) String gw_port,
            @RequestParam(required = false) String gw_id,
            @RequestParam(required = false) String url,
            @RequestParam(required = false) String ip,
            @RequestParam(required = false) String mac) {

        logger.info("login --- gw_address:{}, gw_port:{}, gw_id:{}", new String[]{gw_address, gw_port, gw_id});
        logger.info("login --- ip:{}, mac:{}", ip, mac);

        long l = System.currentTimeMillis();
        String md5Encode = Utils.md5Encode(l + "");

        return new ModelAndView("/wifidog/login")
                .addObject("token", md5Encode)
                .addObject("url", url);
    }

    //Auth:
//    0 - AUTH_DENIED - User firewall users are deleted and the user removed.
//    6 - AUTH_VALIDATION_FAILED - User email validation timeout has occured and user/firewall is deleted
//    1 - AUTH_ALLOWED - User was valid, add firewall rules if not present
//    5 - AUTH_VALIDATION - Permit user access to email to get validation email under default rules
//    -1 - AUTH_ERROR - An error occurred during the validation process
    @RequestMapping("/auth")
    public void auth(@RequestParam(required = false) String stage,
                     @RequestParam(required = false) String ip,
                     @RequestParam(required = false) String mac,
                     @RequestParam(required = false) String token,
                     @RequestParam(required = false) String incoming,
                     @RequestParam(required = false) String outgoing,
                     HttpServletResponse response) {

        logger.info("auth --- stage:{}, ip:{}, mac:{}, token:{}, incoming:{}, outgoing:{}", new String[]{stage, ip, mac, token, incoming, outgoing});
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        writer.write("Auth: 1");
        writer.close();
    }

    @RequestMapping("/portal")
    public ModelAndView portal() {
        return new ModelAndView("/wifidog/portal");
    }

    @RequestMapping("/sample")
    public ModelAndView sample(@RequestParam String token){
        return new ModelAndView("/wifidog/sample");
    }
}
