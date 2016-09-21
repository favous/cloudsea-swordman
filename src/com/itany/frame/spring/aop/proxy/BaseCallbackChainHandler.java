package com.itany.frame.spring.aop.proxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * 链式回调处理基类
 *
 * @author 15041997
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public abstract class BaseCallbackChainHandler {

    // 最终调用目标对象（最内层对象）
    protected Object targetBean;

    // key:方法 value:自定义方法拦截对象集合
    protected LinkedHashMap<Method, List<MethodInterceptor>> linkedHashMap;

    // key:最终调用目标方法 value:链式最初调用目标（最外层的代理对象）
    protected LinkedHashMap<Method, Object> chainHeadObjMap = new LinkedHashMap<Method, Object>();

    public BaseCallbackChainHandler(Object targetBean, LinkedHashMap<Method, List<MethodInterceptor>> LinkedHashMap) {
        super();
        this.targetBean = targetBean;
        this.linkedHashMap = LinkedHashMap;
        initInterceptChain();
    }

    /**
     * 初始化拦截链，加工结果存入chainStartObjMap
     *
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    protected void initInterceptChain() {
        if (linkedHashMap != null && !linkedHashMap.isEmpty()) {

            for (Entry<Method, List<MethodInterceptor>> entry : linkedHashMap.entrySet()) {
                Method method = entry.getKey();
                List<MethodInterceptor> interceptorArray = entry.getValue();

                // 创建的代理对象做为下一次创建的目标对象
                Object target = targetBean;

                // 依照定义的拦截顺序创建代理对象
                for (MethodInterceptor interceptor : interceptorArray) {
                    // 创建代理回调
                    RunInInterceptor runInInterceptor = new RunInInterceptor(interceptor);
                    // 创建代理对象
                    target = createProxyInstance(target, runInInterceptor);
                }

                chainHeadObjMap.put(method, target);
            }
        }
    }

    /**
     * 拦截器的运行对象（需要注入代理对象的回调中）
     *
     * @author 15041997
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    protected class RunInInterceptor {
        // 自定义的拦截对象，注入
        private MethodInterceptor ic;

        public RunInInterceptor(MethodInterceptor ic) {
            this.ic = ic;
        }

        public Object run(Object target, Method method, Object[] args) throws Throwable {
            // 调用封装参数对象
            MethodInvocation methodInvocation = new MethodInvocationImpl(target, method, args);
            // 自定义的拦截对象执行
            return ic.invoke(methodInvocation);
        }
    }

    /**
     * 创建代理对象
     *
     * @param target
     * @param runInInterceptor
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    abstract Object createProxyInstance(final Object target, final RunInInterceptor runInInterceptor);

    /**
     * 根据Method的名称，参数类型，返回类型，异常类型寻找匹配的方法对应对象
     *
     * @param method
     * @param chainHeadObjMap
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public Object getBeanByMethod(Method method, LinkedHashMap<Method, Object> chainHeadObjMap) {
        String mName = method.getName();
        Class<?>[] mTypes = method.getParameterTypes();
        Class<?> mReturnType = method.getReturnType();
        Class<?>[] mExcTypes = method.getExceptionTypes();
        List<Method> likeMethods = new ArrayList<Method>();

        out: for (Method m : chainHeadObjMap.keySet()) {

            if (!m.getName().equals(mName)) {
                continue;
            }

            Class<?>[] types = m.getParameterTypes();
            if (types.length != mTypes.length) {
                continue;
            }
            if (types.length > 0) {
                for (int i = 0; i < types.length; i++) {
                    if (types[i] != mTypes[i]) {
                        continue out;
                    }
                }
            }

            Class<?> returnType = m.getReturnType();
            if (returnType != mReturnType) {
                continue;
            }

            Class<?>[] excTypes = m.getExceptionTypes();
            if (excTypes.length != mExcTypes.length) {
                continue;
            }
            if (excTypes.length > 0) {
                for (int i = 0; i < excTypes.length; i++) {
                    if (excTypes[i] != mExcTypes[i]) {
                        continue out;
                    }
                }
            }

            likeMethods.add(m);
        }

        if (likeMethods.size() == 0) {
            return null;
        } else if (likeMethods.size() == 1) {
            return chainHeadObjMap.get(likeMethods.get(0));
        } else {
            throw new RuntimeException("匹配到的代理对象不止一个，类名：" + method.getDeclaringClass().getName() + "，方法名："
                    + method.getName());
        }
    }

}
