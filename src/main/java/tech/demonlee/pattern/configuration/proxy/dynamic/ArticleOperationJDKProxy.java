package tech.demonlee.pattern.configuration.proxy.dynamic;

import lombok.extern.slf4j.Slf4j;
import tech.demonlee.pattern.configuration.proxy.control.ArticleAuthorityService;
import tech.demonlee.pattern.configuration.proxy.control.ArticleCommentAuthorityService;
import tech.demonlee.pattern.configuration.proxy.control.ArticleScoreAuthorityService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Demon.Lee
 * @date 2020-08-18 10:17
 */
@Slf4j
public class ArticleOperationJDKProxy {

    private final ArticleAuthorityService articleAuthorityService;

    public ArticleOperationJDKProxy(ArticleAuthorityService articleAuthorityService) {
        this.articleAuthorityService = articleAuthorityService;
    }

    public Object createProxy(Object proxiedObject) {
        ArticleOperationInvocationHandler scoreInvocationHandler = new ArticleOperationInvocationHandler(proxiedObject);
        return Proxy.newProxyInstance(
                proxiedObject.getClass().getClassLoader(),
                proxiedObject.getClass().getInterfaces(),
                scoreInvocationHandler);
    }

    /**
     * @author Demon.Lee
     * @date 2020-08-18 09:11
     * @desc 动态代理实现
     */
    private class ArticleOperationInvocationHandler implements InvocationHandler {

        private final Object object;

        public ArticleOperationInvocationHandler(Object object) {
            this.object = object;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (!checkAuthority(method, args)) {
                return null;
            }
            return method.invoke(object, args);
        }

        private boolean checkAuthority(Method method, Object[] args) {
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

}
