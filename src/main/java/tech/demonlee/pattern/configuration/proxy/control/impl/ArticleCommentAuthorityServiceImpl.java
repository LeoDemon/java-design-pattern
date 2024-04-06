package tech.demonlee.pattern.configuration.proxy.control.impl;

import tech.demonlee.pattern.configuration.proxy.control.ArticleCommentAuthorityService;
import tech.demonlee.pattern.configuration.proxy.domain.Article;
import tech.demonlee.pattern.configuration.proxy.domain.User;
import tech.demonlee.pattern.configuration.proxy.service.ArticleService;
import tech.demonlee.pattern.configuration.proxy.control.ArticleCommentAuthorityService;
import tech.demonlee.pattern.configuration.proxy.service.ArticleService;

/**
 * @author Demon.Lee
 * @date 2020-08-20 09:37
 */
public class ArticleCommentAuthorityServiceImpl implements ArticleCommentAuthorityService {

    private final ArticleService articleService;

    public ArticleCommentAuthorityServiceImpl(ArticleService articleService) {
        this.articleService = articleService;
    }

    @Override
    public void auth(long articleId, long commentUserId) throws IllegalAccessException {
        User user = User.get(commentUserId);
        Article article = articleService.get(articleId);
        if (article.isAuthor(commentUserId)) {
            return;
        }
        if (!user.checkRegisterDuration(7 * 24 * 3600 * 1000)) {
            throw new IllegalAccessException(
                    "user:" + user.getName() + " has no authority to comment the article: " + article.getTitle());
        }
    }
}
