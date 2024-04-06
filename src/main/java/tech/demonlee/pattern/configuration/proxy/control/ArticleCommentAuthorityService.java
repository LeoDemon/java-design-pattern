package tech.demonlee.pattern.configuration.proxy.control;

/**
 * @author Demon.Lee
 * @date 2020-08-20 09:35
 */
public interface ArticleCommentAuthorityService extends ArticleAuthorityService {

    /**
     * auth for the comment operation
     *
     * @param articleId     文章id
     * @param commentUserId 评论用户id
     * @throws IllegalAccessException 不合法的操作异常
     */
    void auth(long articleId, long commentUserId) throws IllegalAccessException;
}
