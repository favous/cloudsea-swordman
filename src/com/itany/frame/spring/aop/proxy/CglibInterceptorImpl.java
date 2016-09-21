package com.itany.frame.spring.aop.proxy;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * cglib代理链回调实现类
 *
 * @author 15041997
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class CglibInterceptorImpl extends BaseCallbackChainHandler implements MethodInterceptor {

    public CglibInterceptorImpl(Object targetBean,
            LinkedHashMap<Method, List<org.aopalliance.intercept.MethodInterceptor>> linkedHashMap) {
        super(targetBean, linkedHashMap);
    }

    @Override
    Object createProxyInstance(final Object target, final RunInInterceptor runInInterceptor) {
        MethodInterceptor cginterceptor = new MethodInterceptor() {
            public Object intercept(Object object, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                return runInInterceptor.run(target, method, args);
            }
        };

        CglibFactory factory = new CglibFactory(targetBean.getClass(), cginterceptor);
        return factory.createCglibInstance();
    }

    @Override
    public Object intercept(Object object, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {

        Object target = chainHeadObjMap.get(method);
        if (target != null) {
            return method.invoke(target, args);
        } else {
            return method.invoke(targetBean, args);
        }
    }

}
