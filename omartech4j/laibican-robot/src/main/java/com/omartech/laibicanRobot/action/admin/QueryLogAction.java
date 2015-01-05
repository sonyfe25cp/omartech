package com.omartech.laibicanRobot.action.admin;

import com.omartech.laibicanRobot.model.QueryLog;
import com.omartech.laibicanRobot.service.CenterService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by omar on 15-1-5.
 */
@Controller
@RequestMapping("/admin/query")
public class QueryLogAction {

    @RequestMapping("/list")
    public ModelAndView listQuery(
            @RequestParam(required = false, defaultValue = "0") int pageNo,
            @RequestParam(required = false, defaultValue = "20") int pageSize
    ) {

        int offset = pageNo * pageSize;

        CenterService centerService = new CenterService();
        List<QueryLog> queryLogs = centerService.findQueryLogs(offset, pageSize);

        return new ModelAndView("/admin/query/list").addObject("queryLogs", queryLogs).addObject("pageNo", pageNo).addObject("pageSize", pageSize);
    }


}
