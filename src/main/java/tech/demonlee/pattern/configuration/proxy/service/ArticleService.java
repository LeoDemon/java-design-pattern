package tech.demonlee.pattern.configuration.proxy.service;

import tech.demonlee.pattern.configuration.proxy.domain.Article;

/**
 * @author Demon.Lee
 * @date 2020-08-16 20:13
 */
public interface ArticleService {

    /**
     * create an article via random
     *
     * @return
     */
    Article generateByRandom(long userId);

    /**
     * get Article detail
     *
     * @param id
     * @return
     */
    Article get(long id);
}
