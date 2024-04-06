package tech.demonlee.pattern.configuration.proxy.dynamic;

import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import tech.demonlee.pattern.configuration.proxy.control.ArticleAuthorityService;
import tech.demonlee.pattern.configuration.proxy.control.ArticleCommentAuthorityService;
import tech.demonlee.pattern.configuration.proxy.control.ArticleScoreAuthorityService;

import java.lang.reflect.Method;

/**
 * @author Demon.Lee
 * @date 2020-08-19 09:14
 * @task-decomposition <p>
 * 1. create constructor
 * 2. add createProxy method
 * 3. add checkAutority method
 * </p>
 */
@Slf4j
public class ArticleOperationCglibProxy {

    private final ArticleAuthorityService articleAuthorityService;

    public ArticleOperationCglibProxy(ArticleAuthorityService articleAuthorityService) {
        this.articleAuthorityService = articleAuthorityService;
    }

    @SuppressWarnings("unchecked")
    public <T> T createProxy(Class<T> clazz) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
            if (!checkAuthority(method, args)) {
                return null;
            }
            return proxy.invokeSuper(obj, args);
        });
        return (T) enhancer.create();
    }

    private boolean checkAuthority(Method method, Object[] args) throws IllegalAccessException {
        String methodName = method.getName();
        try {
            if ("comment".equals(methodName)) {
                ((ArticleCommentAuthorityService) articleAuthorityService).auth((long) args[0], (long) args[1]);
            } else if ("score".equals(methodName)) {
                ((ArticleScoreAuthorityService) articleAuthorityService).auth((long) args[0], (long) args[1]);
            }
        } catch (IllegalAccessException ex) {
            log.error("CheckAuthority failed: {}", ex.getMessage());
            return false;
        }
        return true;
    }

}
