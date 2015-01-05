package com.omartech.laibicanRobot.action.admin;

import cn.techwolf.data.gen.Article;
import cn.techwolf.data.gen.ArticleRequest;
import cn.techwolf.data.gen.ArticleResponse;
import cn.techwolf.data.gen.ArticleType;
import com.omartech.engine.client.ClientException;
import com.omartech.engine.client.DataClients;
import com.omartech.laibicanRobot.service.CenterService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * 添加各种文章，区分类型
 * Created by omar on 14-12-20.
 */
@Controller
@RequestMapping("/admin/articles")
public class ArticleAction {
    static Logger logger = LoggerFactory.getLogger(ArticleAction.class);

    @RequestMapping("/list")
    public ModelAndView listArticles(@RequestParam(required = false, defaultValue = "0") int pageNo,
                                     @RequestParam(required = false, defaultValue = "20") int pageSize) {

        ArticleRequest articleRequest = new ArticleRequest();
        articleRequest.setLimit(pageSize);
        articleRequest.setOffset(pageNo * pageSize);
        List<com.omartech.laibicanRobot.model.Article> articles = new ArrayList<>();

        DataClients dataClients = CenterService.fetchClient();
        try {
            ArticleResponse articleResponse = dataClients.searchArticle(articleRequest);
            List<Article> articles1 = articleResponse.getArticles();
            logger.info("fetch {} articles from server", articles1.size());
            for (Article article : articles1) {
                com.omartech.laibicanRobot.model.Article a = new com.omartech.laibicanRobot.model.Article();
                a.setTitle(article.getTitle());
                a.setContent(article.getContent());
                a.setId(article.getId());
                articles.add(a);
            }
        } catch (ClientException e) {
            e.printStackTrace();
        }

        return new ModelAndView("/admin/article/list").addObject("articles", articles);
    }

    @RequestMapping("/add")
    public String addArticle() {
        return "/admin/article/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addArtcilePost(
            @RequestParam String content,
            @RequestParam String type,
            @RequestParam(required = false) String batch
    ) {
        DataClients client = CenterService.fetchClient();

        try {
            if (batch.equals("batch")) {
                String[] strings = content.split("\r\n");
                for (String tmp : strings) {
                    tmp = StringUtils.deleteWhitespace(tmp);
                    if (!StringUtils.isEmpty(tmp)) {
                        Article article = new Article();
                        article.setContent(tmp);
                        ArticleType articleType = ArticleType.valueOf(type);
                        article.setArticleType(articleType);
                        client.insertArticle(article);
                    }
                }
            } else {
                Article article = new Article();
                article.setContent(content);
                ArticleType articleType = ArticleType.valueOf(type);
                article.setArticleType(articleType);
                client.insertArticle(article);
            }
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return "redirect:/admin/articles/list";
    }
}
