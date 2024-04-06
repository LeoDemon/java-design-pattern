package tech.demonlee.pattern.configuration.proxy.service;

/**
 * @author Demon.Lee
 * @date 2020-08-16 20:51
 */
public interface ArticleScoreService {

    void score(long articleId, long userId, int score);
}
