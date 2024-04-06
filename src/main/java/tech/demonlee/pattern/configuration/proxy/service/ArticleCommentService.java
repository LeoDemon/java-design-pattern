package tech.demonlee.pattern.configuration.proxy.service;

/**
 * @author Demon.Lee
 * @date 2020-08-16 20:50
 */
public interface ArticleCommentService {

    void comment(long articleId, long userId, String title, String content);
}
