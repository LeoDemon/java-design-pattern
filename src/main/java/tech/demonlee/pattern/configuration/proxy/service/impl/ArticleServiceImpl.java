package tech.demonlee.pattern.configuration.proxy.service.impl;

import tech.demonlee.pattern.configuration.proxy.domain.Article;
import tech.demonlee.pattern.configuration.proxy.service.ArticleService;
import tech.demonlee.pattern.configuration.proxy.service.ArticleService;

/**
 * @author Demon.Lee
 * @date 2020-08-16 20:52
 */
public class ArticleServiceImpl implements ArticleService {

    private static Article article;

    @Override
    public Article generateByRandom(long userId) {
        article = new Article("TDD入门", "TDD入门说明", userId);
        return article;
    }

    @Override
    public Article get(long id) {
        return article;
    }
}
