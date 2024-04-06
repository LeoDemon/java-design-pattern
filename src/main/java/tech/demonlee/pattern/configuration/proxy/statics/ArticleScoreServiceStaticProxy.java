package tech.demonlee.pattern.configuration.proxy.statics;

import lombok.extern.slf4j.Slf4j;
import tech.demonlee.pattern.configuration.proxy.domain.Article;
import tech.demonlee.pattern.configuration.proxy.domain.User;
import tech.demonlee.pattern.configuration.proxy.service.ArticleScoreService;
import tech.demonlee.pattern.configuration.proxy.service.ArticleService;
import tech.demonlee.pattern.configuration.proxy.service.impl.ArticleServiceImpl;

/**
 * @author Demon.Lee
 * @date 2020-08-16 21:39
 * @desc 静态代理实现
 */
@Slf4j
public class ArticleScoreServiceStaticProxy implements ArticleScoreService {

    private final ArticleScoreService articleScoreService;

    private final ArticleService articleService;

    public ArticleScoreServiceStaticProxy(ArticleScoreService articleScoreService) {
        this.articleScoreService = articleScoreService;
        this.articleService = new ArticleServiceImpl();
    }

    @Override
    public void score(long articleId, long userId, int score) {
        try {
            this.checkAuthority(articleId, userId);
        } catch (IllegalAccessException e) {
            log.error(e.getMessage());
            return;
        }
        this.articleScoreService.score(articleId, userId, score);
    }

    private void checkAuthority(long articleId, long userId) throws IllegalAccessException {
        User user = User.get(userId);
        // log.info("ArticleScoreServiceStaticProxy user: {}", user);
        Article article = this.articleService.get(articleId);
        if (article.isAuthor(userId)) {
            throw new IllegalAccessException("author cannot score!");
        }
        if (!user.checkRegisterDuration(2 * 7 * 24 * 3600 * 1000)) {
            throw new IllegalAccessException(
                    "user:" + user.getName() + " has no authority to score the article:" + article.getTitle());
        }
    }
}
