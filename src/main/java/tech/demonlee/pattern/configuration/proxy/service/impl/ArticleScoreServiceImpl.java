package tech.demonlee.pattern.configuration.proxy.service.impl;

import lombok.extern.slf4j.Slf4j;
import tech.demonlee.pattern.configuration.proxy.domain.ArticleScore;
import tech.demonlee.pattern.configuration.proxy.service.ArticleScoreService;
import tech.demonlee.pattern.configuration.proxy.service.ArticleScoreService;

/**
 * @author Demon.Lee
 * @date 2020-08-16 20:53
 */
@Slf4j
public class ArticleScoreServiceImpl implements ArticleScoreService {

    @Override
    public void score(long articleId, long userId, int score) {
        if (score < 1 || score > 10) {
            throw new IllegalArgumentException("score is invalid");
        }
        ArticleScore articleScore = new ArticleScore(articleId, userId, score);
        log.info("new article score is: {}", articleScore);
    }
}
