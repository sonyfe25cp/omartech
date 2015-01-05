package com.omartech.laibicanRobot.action;

import cn.techwolf.data.gen.Article;
import cn.techwolf.data.gen.ArticleRequest;
import cn.techwolf.data.gen.ArticleResponse;
import com.omartech.engine.client.ClientException;
import com.omartech.engine.client.DataClients;
import com.omartech.laibicanRobot.service.CenterService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by omar on 15-1-5.
 */
@Controller
public class SearchAction {
    static Logger logger = LoggerFactory.getLogger(SearchAction.class);

    @RequestMapping("/search")
    public ModelAndView search(@RequestParam(required = false) String query,
                               @RequestParam(required = false) String appName,
                               @RequestParam(required = false, defaultValue = "0") int pageNo,
                               @RequestParam(required = false, defaultValue = "30") int pageSize
    ) {
        if (StringUtils.isEmpty(query) && StringUtils.isEmpty(appName)) {
            return new ModelAndView("/search");
        }

        DataClients dataClients = CenterService.fetchClient();
        ArticleRequest articleRequest = new ArticleRequest();
        articleRequest.setKeyword(query);
        articleRequest.setOffset(pageNo * pageSize);
        articleRequest.setLimit(pageSize);
        ArticleResponse articleResponse = null;
        try {
            switch (appName) {
                case "Bican":
                    articleResponse = dataClients.searchArticle(articleRequest);
                    break;
                case "Xiaohua":
                    articleResponse = dataClients.searchArticle(articleRequest);
                    break;
                default:
                    break;
            }
        } catch (ClientException e) {
            e.printStackTrace();
        }
        List<Article> articles = articleResponse.getArticles();
        logger.info("fetch {} article", articles.size());
        return new ModelAndView("/search")
                .addObject("articles", articles)
                .addObject("pageNo", pageNo)
                .addObject("pageSize", pageSize);
    }

}
