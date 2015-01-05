package com.omartech.laibicanRobot.action;

import com.omartech.engine.client.ClientException;
import com.omartech.laibicanRobot.model.AppEnum;
import com.omartech.laibicanRobot.model.reply.ReplyMessage;
import com.omartech.laibicanRobot.service.CenterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * Created by omar on 15-1-5.
 */
@Controller
public class WxAction {
    static Logger logger = LoggerFactory.getLogger(WxAction.class);

    @RequestMapping(value = "/testwx", method = RequestMethod.GET)
    public String testGet() {
        return "/common/testWX";
    }

    @RequestMapping(value = "/testwx", method = RequestMethod.POST)
    public void test(
            @RequestParam String word,
            HttpServletResponse response

    ) {
        logger.info("i have receive the post req");
        response.setCharacterEncoding("UTF-8");
        try {
            PrintWriter writer = response.getWriter();

            CenterService centerService = new CenterService();
            ReplyMessage reply = centerService.findAnswer("0001", word, AppEnum.Bican, "admin");

            String xml = reply.toXML();

            writer.write(xml);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
