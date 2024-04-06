package tech.demonlee.pattern.configuration.proxy.control.impl;

import lombok.extern.slf4j.Slf4j;
import tech.demonlee.pattern.configuration.proxy.control.ArticleScoreAuthorityService;
import tech.demonlee.pattern.configuration.proxy.domain.Article;
import tech.demonlee.pattern.configuration.proxy.domain.User;
import tech.demonlee.pattern.configuration.proxy.service.ArticleService;
import tech.demonlee.pattern.configuration.proxy.control.ArticleScoreAuthorityService;
import tech.demonlee.pattern.configuration.proxy.service.ArticleService;

/**
 * @author Demon.Lee
 * @date 2020-08-20 09:24
 */
@Slf4j
public class ArticleScoreAuthorityServiceImpl implements ArticleScoreAuthorityService {

    private final ArticleService articleService;

    public ArticleScoreAuthorityServiceImpl(ArticleService articleService) {
        this.articleService = articleService;
    }

    @Override
    public void auth(long articleId, long scoreUserId) throws IllegalAccessException {
        User user = User.get(scoreUserId);
        Article article = articleService.get(articleId);
        if (article.isAuthor(scoreUserId)) {
            throw new IllegalAccessException("author cannot score!");
        }
        if (!user.checkRegisterDuration(2 * 7 * 24 * 3600 * 1000)) {
            throw new IllegalAccessException(
                    "user:" + user.getName() + " has no authority to score the article: " + article.getTitle());
        }
    }
}
