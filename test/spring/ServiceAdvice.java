package spring;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;


public class ServiceAdvice implements MethodInterceptor {

    private int order;

    /** 调用时间阈值 */
    protected long threshold = 1000L;

    public Object invoke(MethodInvocation invocation) throws Throwable {

        Method method = invocation.getMethod();
        Object target = invocation.getThis();

        String methodName = target.getClass().getSimpleName() + "." + method.getName();

        try {
            Object returnObject = invocation.proceed();
            return returnObject;
        } catch (Throwable t) {
            return this.handleException(method, t, "99", "系统繁忙,请稍后再试！");
        } finally {
            
        }
    }

    /**
     * 处理异常
     * 
     * @param method 方法
     * @param t 异常
     * @param errorCode 错误码
     * @param message 错误信息
     * @return 调用结果
     */
    private Object handleException(Method method, Throwable t, String errorCode, String message) {
        Object result = null;

        return result;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

}
