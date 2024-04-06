package tech.demonlee.pattern.configuration.proxy.domain;

import tech.demonlee.common.Constants;
import tech.demonlee.util.IdUtil;

import java.util.Date;

/**
 * @author Demon.Lee
 * @date 2020-08-16 19:52
 */
public class ArticleComment {

    private final long id;
    private final long userId;
    private String title;
    private String content;
    private final Date createTime;
    private Date lastModifyTime;

    public ArticleComment(long userId, String title, String content) {
        this.id = IdUtil.generate(Constants.NODE_ID_DEFAULT);
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.createTime = new Date();
        this.lastModifyTime = new Date();
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    @Override
    public String toString() {
        return "ArticleComment{" +
                "id=" + id +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", lastModifyTime=" + lastModifyTime +
                '}';
    }
}
