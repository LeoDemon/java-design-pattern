package tech.demonlee.pattern.configuration.proxy.domain;

import tech.demonlee.common.Constants;
import tech.demonlee.util.IdUtil;

import java.util.Date;

/**
 * @author Demon.Lee
 * @date 2020-08-16 17:59
 */
public class Article {

    private final long id;
    private String title;
    private String content;
    private final Date createTime;
    private Date lastModifyTime;
    private final long userId;

    public Article(String title, String content, long userId) {
        this.id = IdUtil.generate(Constants.NODE_ID_DEFAULT);
        this.title = title;
        this.content = content;
        this.lastModifyTime = new Date();
        this.createTime = new Date();
        this.userId = userId;
    }

    public long getId() {
        return id;
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

    public long getUserId() {
        return userId;
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

    public boolean isAuthor(long userId) {
        return this.getUserId() == userId;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", lastModifyTime=" + lastModifyTime +
                ", userId=" + userId +
                '}';
    }
}
