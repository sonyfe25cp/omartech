package com.omartech.laibicanRobot.action;

import com.omartech.data.gen.*;
import com.omartech.engine.client.ClientException;
import com.omartech.engine.client.DataClients;
import com.omartech.laibicanRobot.model.AppEnum;
import com.omartech.laibicanRobot.service.CenterService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
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


    @RequestMapping("/newindex")
    public String wapList() {
        return "/wap/index";
    }


    @RequestMapping("/createArticle")
    public String create() {
        return "/wap/create";
    }

    @RequestMapping(value = "/createArticle", method = RequestMethod.POST)
    public ModelAndView createArticle(@RequestParam String title) {
        Article article = new Article();
        article.setArticleType(ArticleType.Bican);
        article.setContent(title);
        try {
            clients.insertArticle(article);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return new ModelAndView("/wap/story").addObject("article", article);
    }

    @RequestMapping("/choumeinv")
    public String choumeinv() {
        return "/wap/meinv";
    }


    @RequestMapping("/daleitai")
    public ModelAndView leitai() {

        ArticleRequest articleRequest = new ArticleRequest();
        articleRequest.setRandom(true);
        articleRequest.setLimit(2);
        articleRequest.setArticleType(ArticleType.Bican);
        List<Article> articles = null;
        try {
            ArticleResponse response = clients.searchArticle(articleRequest);
            articles = response.getArticles();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return new ModelAndView("/wap/leitai")
                .addObject("articles", articles);

    }

    @RequestMapping("/story")
    public ModelAndView story() {

        ArticleRequest articleRequest = new ArticleRequest();
        articleRequest.setRandom(true);
        articleRequest.setArticleType(ArticleType.Bican);
        Article article = null;
        try {
            ArticleResponse response = clients.searchArticle(articleRequest);
            List<Article> articles = response.getArticles();
            article = articles.get(0);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return new ModelAndView("/wap/story").addObject("article", article);
    }

    @RequestMapping("/kanxiaohua")
    public ModelAndView kanxiaohua(@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
                                   @RequestParam(value = "pageSize", defaultValue = "30", required = false) int pageSize) {
        pageNo = pageNo > 0 ? pageNo : 0;
        int offset = (pageNo + 1) * pageSize;
        ArticleRequest articleRequest = new ArticleRequest();
        articleRequest.setLimit(30);
        articleRequest.setOffset(offset);
        articleRequest.setArticleType(ArticleType.Xiaohua);
        List<Article> articles = null;
        try {
            ArticleResponse response = clients.searchArticle(articleRequest);
            articles = response.getArticles();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return new ModelAndView("/wap/xiaohua")
                .addObject("articles", articles)
                .addObject("pageNo", pageNo);
    }


    static DataClients clients = CenterService.fetchClient();

    @RequestMapping("/wx/{path}")
    public ModelAndView wxPage(@PathVariable String path) {
        logger.info("{} is visiting", path);
        String[] split = path.split("-");
        Article article = null;
        if (split.length == 2) {
            String app = split[0];
            AppEnum appEnum = AppEnum.valueOf(app);
            String id = split[1];

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
