package com.omartech.laibicanRobot.action;

import com.omartech.data.gen.*;
import com.omartech.engine.client.ClientException;
import com.omartech.engine.client.DataClients;
import com.omartech.laibicanRobot.service.CenterService;
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
        return "/wap/index";
    }

    @RequestMapping("")
    public String defaultPage() {
        return "/wap/index";
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
    public ModelAndView create(@RequestParam String commentType) {
        return new ModelAndView("/wap/create").addObject("commentType", commentType);
    }

    @RequestMapping(value = "/createArticle", method = RequestMethod.POST)
    public ModelAndView createArticle(@RequestParam String title, @RequestParam String commentType) {
        Article article = new Article();
        ArticleType articleType = ArticleType.valueOf(commentType);
        if (articleType == null) {
            articleType = ArticleType.Other;
        }
        article.setArticleType(articleType);
        article.setContent(title);
        try {
            logger.info(article.toString());
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

    @RequestMapping("/momoArticle")
    public void momoArticle(@RequestParam int salaryId) {
        logger.info("momo articleId : {}", salaryId);
        Article article = new Article();
        article.setId(salaryId);
        try {
            clients.increaseArticleHot(article);
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/zhazhaArticle")
    public void zhazhaArticle(@RequestParam int salaryId) {
        logger.info("zhazha articleId : {}", salaryId);
        Article article = new Article();
        article.setId(salaryId);
        try {
            clients.decreaseArticleHot(article);
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }


    @RequestMapping("/story")
    public ModelAndView story() {

        ArticleRequest articleRequest = new ArticleRequest();
        articleRequest.setRandom(true);
        articleRequest.setArticleType(ArticleType.Bican);
        articleRequest.setLimit(1);
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
    public ModelAndView kanxiaohua() {
        ArticleRequest articleRequest = new ArticleRequest();
        articleRequest.setLimit(30);
        articleRequest.setRandom(true);
        articleRequest.setArticleType(ArticleType.Xiaohua);
        List<Article> articles = null;
        try {
            ArticleResponse response = clients.searchArticle(articleRequest);
            articles = response.getArticles();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return new ModelAndView("/wap/xiaohua")
                .addObject("articles", articles);
    }


    static DataClients clients = CenterService.fetchClient();

    @RequestMapping("/wx/{path}")
    public ModelAndView wxPage(@PathVariable String path) {
        logger.info("{} is visiting", path);
        String[] split = path.split("-");
        Article article = null;
        if (split.length == 2) {
            String app = split[0];
            ArticleType appEnum = ArticleType.valueOf(app);
            String id = split[1];

            ArticleRequest req = new ArticleRequest();
            List<Long> ids = new ArrayList<>();
            switch (appEnum) {
                case Bican:
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
                case Shudong:
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
                default:
                    break;
            }
        }
//        return new ModelAndView("/common/template").addObject("article", article);
        return new ModelAndView("/wap/story").addObject("article", article);
    }

}
