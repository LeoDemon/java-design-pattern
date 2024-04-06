package tech.demonlee.pattern.configuration.proxy.domain;

import tech.demonlee.common.Constants;
import tech.demonlee.util.IdUtil;

import java.util.Date;

/**
 * @author Demon.Lee
 * @date 2020-08-16 19:57
 * @desc 文章评分记录，评分之后不可更改
 */
public class ArticleScore {

    private final long id;
    private final long articleId;
    private final long userId;
    private final int score;
    private final Date createTime;

    public ArticleScore(long articleId, long userId, int score) {
        this.id = IdUtil.generate(Constants.NODE_ID_DEFAULT);
        this.articleId = articleId;
        this.userId = userId;
        this.score = score;
        this.createTime = new Date();
    }

    public long getId() {
        return id;
    }

    public long getArticleId() {
        return articleId;
    }

    public long getUserId() {
        return userId;
    }

    public int getScore() {
        return score;
    }

    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public String toString() {
        return "ArticleScore{" +
                "id=" + id +
                ", articleId=" + articleId +
                ", userId=" + userId +
                ", score=" + score +
                ", createTime=" + createTime +
                '}';
    }
}
