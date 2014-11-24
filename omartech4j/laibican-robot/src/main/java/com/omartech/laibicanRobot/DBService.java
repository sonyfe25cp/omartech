package com.omartech.laibicanRobot;

import com.omartech.laibicanRobot.model.Article;

/**
 * Created by omar on 14-11-22.
 */
public interface DBService {

    public Article findRandomArticle(String type);


    public Article compareArticleWithId(int id);

}
