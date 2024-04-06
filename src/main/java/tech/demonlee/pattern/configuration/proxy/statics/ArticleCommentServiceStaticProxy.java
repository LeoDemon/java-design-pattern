package tech.demonlee.pattern.configuration.proxy.statics;

import lombok.extern.slf4j.Slf4j;
import tech.demonlee.pattern.configuration.proxy.domain.Article;
import tech.demonlee.pattern.configuration.proxy.domain.User;
import tech.demonlee.pattern.configuration.proxy.service.ArticleCommentService;
import tech.demonlee.pattern.configuration.proxy.service.ArticleService;
import tech.demonlee.pattern.configuration.proxy.service.impl.ArticleServiceImpl;

/**
 * @author Demon.Lee
 * @date 2020-08-16 21:38
 * @desc 静态代理实现
 */
@Slf4j
public class ArticleCommentServiceStaticProxy implements ArticleCommentService {

    private final ArticleCommentService articleCommentService;

    private final ArticleService articleService;

    public ArticleCommentServiceStaticProxy(ArticleCommentService articleCommentService) {
        this.articleCommentService = articleCommentService;
        this.articleService = new ArticleServiceImpl();
    }

    @Override
    public void comment(long articleId, long userId, String title, String content) {
        try {
            this.checkAuthority(articleId, userId);
        } catch (IllegalAccessException e) {
            log.error(e.getMessage());
            return;
        }
        this.articleCommentService.comment(articleId, userId, title, content);
    }

    private void checkAuthority(long articleId, long userId) throws IllegalAccessException {
        User user = User.get(userId);
        // log.info("ArticleScoreServiceStaticProxy user: {}", user);
        Article article = this.articleService.get(articleId);
        if (article.isAuthor(userId)) {
            return;
        }
        if (!user.checkRegisterDuration(7 * 24 * 3600 * 1000)) {
            throw new IllegalAccessException(
                    "user:" + user.getName() + " has no authority to comment the article:" + article.getTitle());
        }
    }
}
