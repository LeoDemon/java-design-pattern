package tech.demonlee.pattern.configuration.proxy.control;

/**
 * @author Demon.Lee
 * @date 2020-08-20 09:19
 */
public interface ArticleScoreAuthorityService extends ArticleAuthorityService {

    /**
     * auth for the score operation
     *
     * @param articleId   文章id
     * @param scoreUserId 打分用户id
     */
    void auth(long articleId, long scoreUserId) throws IllegalAccessException;
}
