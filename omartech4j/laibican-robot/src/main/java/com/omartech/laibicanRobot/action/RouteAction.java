package com.omartech.laibicanRobot.action;

import cn.techwolf.data.gen.*;
import com.omartech.engine.client.ClientException;
import com.omartech.engine.client.DataClients;
import com.omartech.laibicanRobot.model.AppEnum;
import com.omartech.laibicanRobot.service.CenterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

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

    @RequestMapping("/beauties")
    public ModelAndView beautiesList(
            @RequestParam(required = false, defaultValue = "0") int pageNo,
            @RequestParam(required = false, defaultValue = "20") int pageSize,
            @RequestParam(required = false) String tag
    ) {
        DataClients clients = CenterService.fetchClient();
        BeautyRequest req = new BeautyRequest();
        req.setOffset(pageNo * pageSize);
        req.setLimit(pageSize);
        req.setQuery(tag);
        List<Beauty> beauties = null;
        try {
            BeautyResponse response = clients.searchBeauty(req);
            beauties = response.getBeauties();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return new ModelAndView("/beauty/list").addObject("beauties", beauties)
                .addObject("pageNo", pageNo).addObject("pageSize", pageSize);
    }


//    @RequestMapping(value = "/testt", method = RequestMethod.GET)
//    public String test() {
//        return "/test";
//    }
//
//    @RequestMapping(value = "/test", method = RequestMethod.GET)
//    public ModelAndView test(@RequestParam String a) {
//        logger.info("测试，哈哈:{}", a);
//        return new ModelAndView("/res").addObject("b", a);
//    }

//    @RequestMapping(value = "/test", method = RequestMethod.POST)
//    public ModelAndView test2(@RequestParam String a) {
//        logger.info("测试，哈哈2:{}", a);
//        return new ModelAndView("/res").addObject("b", a);
//    }

    @RequestMapping("/wx/{path}")
    public ModelAndView wxPage(@PathVariable String path) {
        logger.info("{} is visiting", path);
        String[] split = path.split("-");
        Article article = null;
        if (split.length == 2) {
            String app = split[0];
            AppEnum appEnum = AppEnum.valueOf(app);
            String id = split[1];

            DataClients clients = CenterService.fetchClient();
            switch (appEnum) {
                case Bican:
                    ArticleRequest req = new ArticleRequest();
                    List<Long> ids = new ArrayList<>();
                    ids.add(Long.parseLong(id));
                    req.setIds(ids);
                    try {
                        ArticleResponse articleResponse = clients.searchArticle(req);
                        List<Article> articles = articleResponse.getArticles();
                        for (Article art : articles) {
                            article = art;
                        }
                    } catch (ClientException e) {
                        e.printStackTrace();
                    }
                    break;
                case Hero:
                    break;
                case Jobs:
                    break;
                case Other:
                    break;
                default:
                    break;
            }
        }
        return new ModelAndView("/common/template").addObject("article", article);
    }

}
