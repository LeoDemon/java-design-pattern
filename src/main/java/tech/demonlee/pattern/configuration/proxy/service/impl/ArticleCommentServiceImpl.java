package tech.demonlee.pattern.configuration.proxy.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import tech.demonlee.pattern.configuration.proxy.domain.ArticleComment;
import tech.demonlee.pattern.configuration.proxy.service.ArticleCommentService;
import tech.demonlee.pattern.configuration.proxy.service.ArticleCommentService;

/**
 * @author Demon.Lee
 * @date 2020-08-16 20:52
 */
@Slf4j
public class ArticleCommentServiceImpl implements ArticleCommentService {

    @Override
    public void comment(long articleId, long userId, String title, String content) {
        if (StringUtils.isBlank(title) || StringUtils.isBlank(content)) {
            throw new IllegalArgumentException("invalid title or content");
        }
        ArticleComment comment = new ArticleComment(userId, title, content);
        log.info("new ArticleComment is: {}", comment);
    }
}
