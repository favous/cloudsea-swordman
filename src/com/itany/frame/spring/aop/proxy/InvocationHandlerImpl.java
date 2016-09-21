package com.itany.frame.spring.aop.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;

import org.aopalliance.intercept.MethodInterceptor;

/**
 * proxy代理代理链回调实现类
 *
 * @author 15041997
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class InvocationHandlerImpl extends BaseCallbackChainHandler implements InvocationHandler {

    public InvocationHandlerImpl(Object targetBean, LinkedHashMap<Method, List<MethodInterceptor>> LinkedHashMap) {
        super(targetBean, LinkedHashMap);
    }

    @Override
    Object createProxyInstance(final Object target, final RunInInterceptor runInInterceptor) {
        InvocationHandler invocationHandler = new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return runInInterceptor.run(target, method, args);
            }
        };
        ClassLoader classLoader = super.targetBean.getClass().getClassLoader();
        Class<?>[] interfaceClasses = super.targetBean.getClass().getInterfaces();
        ProxyFactory factory = new ProxyFactory(classLoader, interfaceClasses, invocationHandler);
        return factory.createProxyInstance();
    }

    @Override
    public Object invoke(Object proxy, Method method, final Object[] args) throws Throwable {

        Object target = chainHeadObjMap.get(method);

        if (target == null) {
            target = getBeanByMethod(method, chainHeadObjMap);
        }

        if (target == null) {
            target = targetBean;
        }

        return method.invoke(target, args);
    }

}
