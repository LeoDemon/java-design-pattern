package tech.demonlee.pattern.configuration.proxy;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tech.demonlee.pattern.configuration.proxy.control.ArticleAuthorityService;
import tech.demonlee.pattern.configuration.proxy.control.impl.ArticleCommentAuthorityServiceImpl;
import tech.demonlee.pattern.configuration.proxy.control.impl.ArticleScoreAuthorityServiceImpl;
import tech.demonlee.pattern.configuration.proxy.domain.Article;
import tech.demonlee.pattern.configuration.proxy.domain.User;
import tech.demonlee.pattern.configuration.proxy.dynamic.ArticleOperationCglibProxy;
import tech.demonlee.pattern.configuration.proxy.dynamic.ArticleOperationJDKProxy;
import tech.demonlee.pattern.configuration.proxy.service.ArticleCommentService;
import tech.demonlee.pattern.configuration.proxy.service.ArticleScoreService;
import tech.demonlee.pattern.configuration.proxy.service.ArticleService;
import tech.demonlee.pattern.configuration.proxy.service.impl.ArticleCommentServiceImpl;
import tech.demonlee.pattern.configuration.proxy.service.impl.ArticleScoreServiceImpl;
import tech.demonlee.pattern.configuration.proxy.service.impl.ArticleServiceImpl;
import tech.demonlee.pattern.configuration.proxy.statics.ArticleCommentServiceStaticProxy;
import tech.demonlee.pattern.configuration.proxy.statics.ArticleScoreServiceStaticProxy;
import tech.demonlee.pattern.configuration.proxy.statics.ArticleCommentServiceStaticProxy;
import tech.demonlee.pattern.configuration.proxy.statics.ArticleScoreServiceStaticProxy;

import java.util.List;

/**
 * @author Demon.Lee
 * @date 2020-08-03 22:52
 * @desc 代理设计模式学习:
 * 在不改变原始类（或叫被代理类）代码的情况下，通过引入代理类来给原始类附加功能。
 * 代理模式在业务系统中开发一些非功能性需求，比如：监控、鉴权、限流、幂等、统计、事务、日志等
 * <p>
 * <p>
 * 1. 静态代理
 * 1.1 基于接口的静态代理
 * 1.2 基于类继承的静态代理
 * 2. 动态代理
 * 2.1 jdk proxy
 * 2.2 cglib proxy
 * <p>
 * <p>
 * 业务描述：
 * 1. 给播客文章写评论和打分
 * 2. 读者用户注册时间超过1周才可以评论（作者不限）
 * 3. 读者用户注册时间超过2周才可以打分（作者不能打分）
 * <p>
 * Actor（涉众）：
 * Author：作者
 * Reader: 读者
 * 注意：他们都属于用户，用户都可以认为是读者，但少数是作者
 * <p>
 * 功能分析：
 * 1. 文章管理
 * 1.1 文章评论
 * 1.2 文章打分（读者）,打分不能更改
 * 1.3 判断某用户是作者还是读者
 * 2. 用户管理
 * 1.1 判断用户的注册时长是否超过1周
 * <p>
 * 模型设计：
 * 1. Glossary
 * 1.1 User：用户
 * 1.2 Article：文章
 * 1.3 ArticleComment: 文章评论
 * 1.4 ArticleScore: 文章评分
 * 2. Ubiquitous Language
 * 2.1 User创建一篇Article
 * 2.2 特定User可以评论一篇Article
 * 2.3 特定User可以给一篇Article打分
 * 3. Object Model
 * 3.1 User
 * {
 * field:
 * id
 * name
 * createTime
 * lastModifyTime
 * method:
 * checkRegisterDuration(long duration)
 * }
 * 3.2 Article
 * {
 * field:
 * id
 * title
 * userId
 * createTime
 * lastModifyTime
 * content
 * method:
 * isAuthor(long userId)
 * }
 * 3.3 ArticleComment
 * {
 * field:
 * id
 * userId
 * title
 * content
 * createTime
 * lastModifyTime
 * method:
 * <p>
 * }
 * 3.4 ArticleScore
 * {
 * field:
 * id
 * articleId
 * userId
 * score
 * createTime
 * lastModifyTime
 * }
 * 4. Service
 * 4.1 UserService
 * 4.2 ArticleService
 * 4.3 ArticleCommentService
 * 4.4 ArticleScoreService
 */
@Slf4j
public class MainTest {

    private User userA;
    private User userB;
    private User userC;
    private ArticleCommentService articleCommentService;
    private ArticleScoreService articleScoreService;
    ArticleService articleService;

    @BeforeEach
    public void generateUsers() {
        List<User> users = User.generate();
        this.userA = users.get(0);
        this.userB = users.get(1);
        this.userC = users.get(2);
        articleService = new ArticleServiceImpl();
    }

    /**
     * static proxy test
     * <p>
     * Task decomposition
     * 1) modify User.java: generate user:A、B、C， A register time > 1 week, B register time < 1 week, C register time
     * > 2 week
     * 2) modify ArticleService.java: add 'create' method
     * 3) modify ArticleCommentService.java: add 'comment' method
     * 4) modify ArticleScoreService.java: add 'score' method
     */
    @Test
    public void testStaticProxy() {
        articleCommentService = new ArticleCommentServiceStaticProxy(new ArticleCommentServiceImpl());
        articleScoreService = new ArticleScoreServiceStaticProxy(new ArticleScoreServiceImpl());

        testProxy();
    }

    private void testProxy() {
        Article article = articleService.generateByRandom(userA.getId());
        articleCommentService.comment(article.getId(), userA.getId(), "c1", "comment content-1");
        articleCommentService.comment(article.getId(), userB.getId(), "c2", "comment content-2");
        articleCommentService.comment(article.getId(), userC.getId(), "c3", "comment content-3");
        articleScoreService.score(article.getId(), userA.getId(), 6);
        articleScoreService.score(article.getId(), userB.getId(), 9);
        articleScoreService.score(article.getId(), userC.getId(), 5);
    }

    /**
     * jdk dynamic proxy test
     * 通过反射实现，需要有统一的接口
     */
    @Test
    public void testJdkDynamicProxy() {
        ArticleAuthorityService authorityService = new ArticleCommentAuthorityServiceImpl(articleService);
        ArticleOperationJDKProxy proxy = new ArticleOperationJDKProxy(authorityService);
        articleCommentService = (ArticleCommentService) proxy.createProxy(new ArticleCommentServiceImpl());

        authorityService = new ArticleScoreAuthorityServiceImpl(articleService);
        proxy = new ArticleOperationJDKProxy(authorityService);
        articleScoreService = (ArticleScoreService) proxy.createProxy(new ArticleScoreServiceImpl());

        testProxy();
    }

    /**
     * cglib dynamic proxy test
     * 通过字节码实现，动态创建被代理类的子类，不需要有统一的接口，入侵性更小，性能更高
     */
    @Test
    public void testCglibDynamicProxy() {
        ArticleAuthorityService authorityService = new ArticleCommentAuthorityServiceImpl(articleService);
        ArticleOperationCglibProxy proxy = new ArticleOperationCglibProxy(authorityService);
        articleCommentService = proxy.createProxy(ArticleCommentServiceImpl.class);

        authorityService = new ArticleScoreAuthorityServiceImpl(articleService);
        proxy = new ArticleOperationCglibProxy(authorityService);
        articleScoreService = proxy.createProxy(ArticleScoreServiceImpl.class);

        testProxy();
    }

    /**
     * spring DI is using jdk dynamic proxy or cglib dynamic proxy?
     */
    @Test
    public void testSpringDI() {
    }
}
